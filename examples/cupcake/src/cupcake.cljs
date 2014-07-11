(ns omreactpixi.examples.cupcake
  (:require-macros [omreactpixi.core :as orp]
                   [cljs.core.async.macros :refer [go]])
  (:require [goog.events :as events]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [clojure.string :as string]
            [cljs.core.async :as async :refer [>! <! put!]]
            [omreactpixi.core :as pixi]))


(defn assetpath [name] (str "../assets/" name))

(def flavormapping
  {
   :vanilla (assetpath "creamVanilla.png")
   :chocolate (assetpath "creamChoco.png")
   :mocha (assetpath "creamMocha.png")
   :pink (assetpath "createmPink.png")
   })

(defn cupcakecomponent [cupcakedata owner]
  (reify
    om/IRender
    (render [_]
            (let [creamimage (flavormapping (:topping cupcakedata))
                  cakeimage (assetpath "cupCake.png")
                  xposition (:xposition cupcakedata)
                  spritecenter (PIXI.Point. 0.5 0.5)]
              (pixi/displayobjectcontainer
               #js {:x xposition, :y 100}
               (pixi/sprite #js {:image creamimage :y -35 :anchor spritecenter :key "topping"})
               (pixi/sprite #js {:image cakeimage :y 35 :anchor spritecenter :key "cake"}))))
    om/IDisplayName
    (display-name [_] "Cupcake")))

(defn cupcakestage [app owner]
  (reify
    om/IRender
    (render [_]
            (let [{:keys [width height xposition topping]} app]
              (pixi/stage #js {:width width :height height}
               (pixi/tilingsprite #js {:image (assetpath "bg_castle.png") :width width :height height :key 1})
               (om/build cupcakecomponent {:topping topping :xposition xposition :ref "cupcake" :key 2})
               (pixi/text #js {:text "Vector text" :x xposition :y 10 :style #js {:font "40px Times"} :anchor (PIXI.Point. 0.5 0) :key 3})
               (pixi/bitmaptext #js {:text "Bitmap text" :x xposition :y 180 :tint 16rff88ff88 :style #js {:font "40 Comic_Neue_Angular"} :key 4}))))
    om/IDisplayName
    (display-name [_] "CupcakeStage")))


(defn startcupcakes [w h]
  (om/root cupcakestage  {:width w :height h :xposition 100 :topping :vanilla}
           {:target (.getElementById js/document "my-app")}))

;; gotta load the bitmap font first or else pixi bombs out

(let [inset #(- % 16)
      w (-> js/window .-innerWidth inset)
      h (-> js/window .-innerHeight inset)
      fontloader (PIXI.BitmapFontLoader. (assetpath "comic_neue_angular_bold.fnt"))]
  (.on fontloader "loaded" #(startcupcakes w h))
  (.load fontloader))
