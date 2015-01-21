(ns tests.composite-updates
  (:require-macros [cemerick.cljs.test
                    :refer (is deftest with-test run-tests testing test-var)])
  (:require [om.core :as om :include-macros true]
            [omreactpixi.abbrev :as pixi]
            [cemerick.cljs.test :as t]))

(defn maincomponent [app owner]
  (om/component
    (pixi/stage {:width 600 :height 480 :backgroundcolor 0x888888})))

(defn maintest []
  (om/root
    maincomponent
    {}
    {:target (. js/document (getElementById "test-box"))}))

(deftest somewhat-less-wat
  (is (= 3 (+ 1 2))))
  
