(ns webserver.core
  (use [ring.middleware.file]
       [ring.middleware.file-info]))

(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (System/getProperty "user.dir") })

(def statichandler
   (-> handler
       (wrap-file (System/getProperty "user.dir"))
       (wrap-file-info)))


