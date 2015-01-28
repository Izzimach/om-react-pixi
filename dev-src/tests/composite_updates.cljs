(ns tests.composite-updates
  (:require-macros [cemerick.cljs.test
                    :refer (is deftest done with-test with-test-ctx run-tests testing test-var use-fixtures)])
  (:require [om.core :as om :include-macros true]
            [omreactpixi.abbrev :as pixi :include-macros true]
            [cemerick.cljs.test :as t]))

(enable-console-print!)

;;
;; here we just check that nothing explodes
;;

(defn- simpletestcomponent [app owner]
  (om/component
    (pixi/stage {
                 :width 600 
                 :height 480 
                 :backgroundcolor 0x888888})))

(deftest simplepixitest []
  (om/root
    simpletestcomponent
    {}
    {:target (.getElementById js/document "test-element")}))

;;
;; There is a special situation that forces a composite component to
;; replace its child which results in React trying to set HTML markup on PIXI nodes.
;; since PIXI nodes aren't HTML this will bomb out unless the monkey-patched
;; version of createClass is in-place and working
;;

(def compositetestappstate (atom {}))

(defmulti compositetestcomponent :state :default :loading)

(defmethod compositetestcomponent :loading [app owner]
  (reify
    om/IRender
    (render [_]
      (pixi/displayobjectcontainer
       {}
       (pixi/text {:text "Click here to load"
                   :x 30
                   :y 30})))))

(defmethod compositetestcomponent :loaded [app owner]
  (reify
    om/IRender
    (render [_]
      (pixi/text {:text "Loaded!"}))))

(defn- runcompositetest [test-context]
  (with-test-ctx test-context
                 (om/root
                   (fn [app owner]
                     (om/component
                       (pixi/stage {:width           640
                                    :height          480
                                    :backgroundcolor 0x888888}
                                   (om/build compositetestcomponent app))))
                   compositetestappstate
                   {:target (.getElementById js/document "test-element")})
                 (swap! compositetestappstate (fn [c] assoc c :state :loaded))
                 #_(done)
                 #_(js/setInterval (fn [] (done)) 100)))

#_(deftest compositetest []
  (runcompositetest -test-ctx))

;;
;; this is the same test as the previous one using multiple reify statements
;; instead of a multimethod
;;

(def componenttest2appstate (atom {}))

(defn compositetestcomponent2 [app owner]
  (if (:state app)
    (reify
      om/IRender
      (render [_]
        (pixi/text {:text "Winner!"})))
    (reify
      om/IRender
      (render [_]
        (pixi/displayobjectcontainer
         {}
         (pixi/text {:text "Click here to win"
                     :x 30
                     :y 30}))))))

(defn runcompositetest2 [test-context]
  (with-test-ctx test-context
    (om/root
     (fn [app owner]
       (om/component
        (pixi/stage {:width 640
                     :height 480
                     :backgroundcolor 0x888888}
                    (om/build compositetestcomponent2 app))))
     componenttest2appstate
     {:target (. js/document (getElementById "test-element"))})
    (swap! componenttest2appstate (fn [c] (assoc c :state :loaded)))
    (js/setInterval (fn [] (done))
                    100)))

#_(deftest ^:async compositetest2 []
  (runcompositetest2 -test-ctx))


;;
;; Another composite test: when a composite replaces its child, it
;; needs to properly update the corresponding PIXI scene graph as well as
;; its own internal data. Failure to do this means children sometimes
;; vanish or don't go away when they should
;;


(def componenttest3appstate (atom {:hello "hello"}))

(defn compositetestcomponent3 [app owner]
  (om/component
    (if (:loaded app)
      (pixi/text {:text {:hello app}})
      (pixi/displayobjectcontainer
        {}
        (pixi/text {:text "loading"})))))

(defn runcompositetest3 [test-context]
  (with-test-ctx test-context
                 (let [reactinstance (om/root
                                       (fn [app owner]
                                         (om/component
                                           (pixi/stage {:width           640
                                                        :height          480
                                                        :backgroundcolor 0x888888
                                                        :ref  "stage"}
                                                       (om/build compositetestcomponent3 app))))
                                       componenttest3appstate
                                       {:target (. js/document (getElementById "test-element"))})
                       rootrefs (.-refs reactinstance)
                       stagenode (aget rootrefs "stage")
                       stagedisplayobject (.-displayObject stagenode)]
                   (is (= 1 (.-length (.-children stagedisplayobject))))
                   (swap! componenttest3appstate (fn [c] (assoc c :loaded true)))
                   (js/setInterval (fn []
                                     (swap! componenttest3appstate (fn [c] (assoc c :hello "huh?")))
                                     (js/setInterval (fn []
                                                       (is (= 1 (.-length (.-children stagedisplayobject))))
                                                       (done))
                                                     100))
                                   100))))

(deftest ^:async compositetest3 []
         (runcompositetest3 -test-ctx))

;;
;; basic test fixture
;;

(defn- create-react-mountpoint [f]
  (let [mountpoint (.createElement js/document "div")]
    (aset mountpoint "id" "test-element")
    (.appendChild js/document.body mountpoint)
    (f)
    #_(om/detach-root mountpoint)
    #_(.removeChild js/document.body mountpoint))
  )

(use-fixtures :once create-react-mountpoint)

  
