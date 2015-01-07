;; macros need to be in a clojure (not clojurescript) file

(ns omreactpixi.core)

;; for some reason I made react-pixi components camelcase,
;; so here we convert the names so that they are called as lowercase
;; names in clojurescript

(defmacro defn-pixi-element [tag]
  (let [jsname  (symbol "js" (str "ReactPIXI." (name tag)))
        cljname (symbol (clojure.string/lower-case (name tag)))]
  `(defn ~cljname [opts# & children#]
     (.apply React.createElement
             ~jsname
             nil  ;; context (javascript this)
             (cljs.core/into-array (cons opts# children#))))))
