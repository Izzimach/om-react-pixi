(ns omreactpixi.examples.hello
  (:require [goog.events :as events]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [omreactpixi.abbrev :as pixi :include-macros true]
            [clojure.string :as string]
            [figwheel.client :as fw]))

(enable-console-print!)

(defonce app-state (atom {:hello "hello"}))

(defn game [app owner]
  (om/component
   (if (:loaded app)
     (pixi/text {:text (:hello app)})
     (pixi/displayobjectcontainer
      {}
      (pixi/text {:text "loading"})))))


(defn main []
  (om/root
   (fn [app owner]
     (reify
       om/IRender
       (render [_]
         (pixi/stage {:width 640
                      :height 480
                      :backgroundcolor 0x888888
			:ref "stage"}
                     (om/build game app)))))
   app-state
   {:target (. js/document (getElementById "my-app"))}))


(main)

(fw/start {
	:on-jsload (fn [] (print "reloaded"))})

