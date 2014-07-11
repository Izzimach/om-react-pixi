(ns omreactpixi.examples.cupcake
  (:require-macros [omreactpixi.core :as orp]
                   [cljs.core.async.macros :refer [go]])
  (:require [goog.events :as events]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [clojure.string :as string]
            [cljs.core.async :as async :refer [>! <! put!]]
            [omreactpixi.core :as pixi]))

;;
;; to add to the confusion, there are two pixi namespaces:
;; 1. 'pixi' contains react-pixi components usable with om
;; 2. 'js/PIXI' is the raw PIXI module

(defn assetpath [name] (str "../assets/" name))

(def preloadlist (map assetpath ["comic_neue_angular_bold.fnt" "creamVanilla.png" "creamChoco.png" "creamPink.png" "creamMocha.png"]))

(def appstate (atom {:text "argh!" :preloadmanifest preloadlist}))


(defn pixiloadchannel
  "Pass in a vector of URI's to load. Add ':crossdomain true' if you want x-domain loading.
  Creates a PIXI AssetLoader and returns a channel. Each onProgress event contents are dumped
  into the channel (content is the relevant loader). The channel is closed when
  all assets are loaded (onComplete)."
  [preloadlist & {:keys [crossdomain] :or {crossdomain false}}]
  (let [statuschannel (async/chan)
        assetloader (js/PIXI.AssetLoader. (into-array preloadlist) crossdomain)]
    (set! (.-onProgress assetloader) #(put! statuschannel %))
    (set! (.-onComplete assetloader) #(cljs.core.async/close! statuschannel))
    (.load assetloader)
    statuschannel))


(defn load-then
  "Loads all the assets in the given preloader list and then calls the specified 'done function'"
  [preloadlist donefn]
  (let [loadingstatuschannel (pixiloadchannel preloadlist)]
    (go
     (loop [status (<! loadingstatuschannel)]
       #_(js/console.log status)
       (if-let [nextstatus (<! loadingstatuschannel)]
         (recur nextstatus)
         (donefn))))
    ))

(defn autostage
  "If the cursor contains a :preloadmanifest key, this component first displays the loading screen
  component and then, once all assets in the manifest are loaded, displays the main screen
  component."
  [cursor owner]
  (let [loadopts #js {:width 400 :height 300 :backgroundcolor 16rff00ff}
        mainopts #js {:width 400 :height 300 :backgroundcolor 16r00ffff}
        textopts #js {:x 100 :y 100 :key "ack" :text "Blankity-blank!"}]
    (reify
      om/IInitState
      (init-state [_] {:preloading (contains? cursor :preloadmanifest)})
      om/IDidMount
      (did-mount [_] (when (contains? cursor :preloadmanifest)
                       (load-then (:preloadmanifest cursor) #(om/set-state! owner :preloading false))))
      om/IRenderState
      (render-state [_ state]
                    (if (:preloading state)
                      (pixi/stage loadopts (pixi/text {:x 10 :y 10 :text "Loading"}))
                      (pixi/stage mainopts (pixi/text textopts))))
      om/IDisplayName
      (display-name [_] "Autostage"))))


(defn starthelloworld [appstate elementid]
  (om/root autostage appstate
           {:target (.getElementById js/document elementid)}))


(starthelloworld appstate "my-app")
