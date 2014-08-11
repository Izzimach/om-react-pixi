(ns omreactpixi.examples.hello
  (:require [goog.events :as events]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [omreactpixi.core :as pixi :include-macros true]
            [clojure.string :as string]))

(def appstate (atom {:text "argh!"}))

(defn simplestage [cursor]
  (om/component
   (pixi/stage
    #js {:width 400 :height 300}
    (pixi/text #js {:x 100 :y 100 :text "argh!"}))))

(defn starthelloworld [appstate elementid]
  (om/root simplestage appstate
           {:target (.getElementById js/document elementid)}))


(starthelloworld appstate "my-app")
