(ns omreactpixi.core
  (:require-macros [omreactpixi.core :as orp])
  (:require [goog.events :as events]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [clojure.string :as string]))

(def appstate (atom {:text "argh!"}))

;;
;; need to make these more automated, om-style
;;

(orp/defn-pixi-element Stage)
(orp/defn-pixi-element Text)
(orp/defn-pixi-element BitmapText)
(orp/defn-pixi-element Sprite)
(orp/defn-pixi-element DisplayObjectContainer)
(orp/defn-pixi-element TilingSprite)

(defn autostage [cursor]
  (let [opts #js {:width 400 :height 300}]
    (reify
      om/IRender
      (render [_]
              (stage opts (text #js {:x 100 :y 100 :key "ack" :text "Argadasdh!"})))
      om/IDisplayName
      (display-name [_] "Autostage"))))

(defn stagewidget [cursor]
  (let [opts #js {:width 400 :height 300}]
    (reify
      om/IRender
      (render [_]
              (stage opts (text #js {:x 100 :y 100 :key "ack" :text "Argadasdh!"})))
      om/IDisplayName
      (display-name [_] "Stagewidget"))))


(defn starthelloworld [appstate elementid]
  (om/root autostage appstate
           {:target (.getElementById js/document elementid)}))


(starthelloworld appstate "my-app")
