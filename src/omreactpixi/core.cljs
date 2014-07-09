(ns omreactpixi.core
  (:require-macros [omreactpixi.core :as orp])
  (:require [goog.events :as events]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [clojure.string :as string]))

;;
;; need to make these more automatic, om-style
;;

(orp/defn-pixi-element Stage)
(orp/defn-pixi-element Text)
(orp/defn-pixi-element BitmapText)
(orp/defn-pixi-element Sprite)
(orp/defn-pixi-element DisplayObjectContainer)
(orp/defn-pixi-element TilingSprite)

