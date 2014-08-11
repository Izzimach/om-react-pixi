(ns omreactpixi.abbrev
  (:require-macros [omreactpixi.abbrev :as abbrev])
  (:require [goog.events :as events]
            [clojure.string :as string]))

(defn element-args [opts children]
  (cond
   (nil? opts) [nil children]
   (map? opts) [(clj->js opts) children]
   (object? opts) [opts children]
   :else [nil (cons opts children)]))


(abbrev/defn-pixi-element-abbrev Stage)
(abbrev/defn-pixi-element-abbrev Text)
(abbrev/defn-pixi-element-abbrev BitmapText)
(abbrev/defn-pixi-element-abbrev Sprite)
(abbrev/defn-pixi-element-abbrev DisplayObjectContainer)
(abbrev/defn-pixi-element-abbrev TilingSprite)
