(ns omreactpixi.core
  (:require-macros [omreactpixi.core :as orp])
  (:require [goog.events :as events]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

(def appstate (atom {:text "argh!"}))

(defn text [opts & children]
  (.apply js/ReactPIXI.Text nil (cljs.core/into-array (cons opts children))))

(defn stagewidget [cursor]
  (let [opts #js {:width 400 :height 300}]
    (reify
      om/IRender
      (render [_]
              (js/ReactPIXI.Stage opts (text #js {:x 100 :y 100 :key "ack" :text "Argadasdh!"})))
      om/IDisplayName
      (display-name [_] "StageWidget"))))

(defn starthelloworld [appstate elementid]
  (om/root stagewidget appstate
           {:target (.getElementById js/document elementid)}))


(starthelloworld appstate "my-app")
