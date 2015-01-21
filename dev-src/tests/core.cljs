(ns tests.core)

(def success 0)


(defn ^:export run []
  (.log js/console "Example test started.")
  success)
