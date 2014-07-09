(ns omreactpixi.hello
  (:require-macros [omreactpixi.core :as orp])
  (:require [goog.events :as events]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [omreactpixi.core :as pixi]
            [clojure.string :as string]))

(def appstate (atom {:text "argh!"}))


(defn autostage [cursor]
  (let [opts #js {:width 400 :height 300}
        textopts #js {:x 100 :y 100 :key "ack" :text "Blankity-blank!"}]
    (reify
      om/IInitState
      (init-state [_] {:preloading true})
      om/IRenderState
      (render-state [_ state]
              (pixi/stage opts (pixi/text textopts)))
      om/IDisplayName
      (display-name [_] "Autostage"))))


(defn starthelloworld [appstate elementid]
  (om/root autostage appstate
           {:target (.getElementById js/document elementid)}))


(starthelloworld appstate "my-app")
