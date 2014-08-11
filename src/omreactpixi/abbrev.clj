(ns omreactpixi.abbrev)

;;
;; abbreviated version similar to that used in om-tools
;;


(defmacro defn-pixi-element-abbrev [tag]
  (let [jsname  (symbol "js" (str "ReactPIXI." (name tag)))
        cljname (symbol (clojure.string/lower-case (name tag)))]
    `(defn ~cljname [opts# & children#]
       (let [[opts# children#] (element-args opts# children#)]
         (apply ~jsname (cljs.core/into-array (cons opts# children#)))))))
