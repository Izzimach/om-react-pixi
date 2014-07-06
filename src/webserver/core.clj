(ns webserver.core
  (use [ring.middleware.file]
       [ring.middleware.file-info]))

(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Hello World argh"})

(def statichandler
   (-> handler
       (wrap-file ".")
       (wrap-file-info)))


