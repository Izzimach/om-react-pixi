(ns omreactpixi.examples.hello
  (:require [goog.events :as events]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [omreactpixi.core :as pixi :include-macros true]
            [clojure.string :as string]
            [figwheel.client :as fw]))

(defonce appstate (atom {:text "argh!"}))
(enable-console-print!)


(defn simplestage [cursor]
  (om/component
    (pixi/stage
      #js {:width 400 :height 300}
      (pixi/text #js {:x 100 :y 100 :text (:text cursor)}))))

(defn starthelloworld [appstate elementid]
  (om/root simplestage appstate
           {:target (.getElementById js/document elementid)}))


(starthelloworld appstate "my-app")

;; enable dynamic reloading via figwheel
(fw/watch-and-reload
  :jsload-callback (fn [] (print "reloaded!")))

