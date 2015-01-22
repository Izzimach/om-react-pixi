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
;; special situation that forces PIXI to replace a composite component child
;; in-place, which results in React trying to set HTML markup on PIXI nodes.
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
        (pixi/stage {:width 640
                     :height 480
                     :backgroundcolor 0x888888}
                    (om/build compositetestcomponent app))))
     compositetestappstate
     {:target (.getElementById js/document "test-element")})
    (let [delayedinvoke (fn [delaytime f]
                          (js/setInterval f delaytime))]
      (delayedinvoke 1000 (fn []
                            (swap! compositetestappstate (fn [c] assoc c :state :loaded))
                            (delayedinvoke 1000 (fn [] (done))))))))

#_(deftest ^:async compositetest []
  (runcompositetest -test-ctx))

;;
;; another composite test
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
    (js/setInterval (fn [] (done)) 1000)))

(deftest ^:async compositetest2 []
  (runcompositetest2 -test-ctx))

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

  
