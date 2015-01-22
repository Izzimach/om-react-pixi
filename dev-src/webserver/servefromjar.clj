(ns webserver.servefromjar
  (require [ring.middleware.file :refer [wrap-file]]
           [ring.middleware.file-info :refer [wrap-file-info]]
	   [compojure.core :refer [defroutes GET]]
	   [compojure.route :refer [files resources]]))

(defn handler [request]
  {:status 200
     :headers {"Content-Type" "text/html"}
        :body (System/getProperty "user.dir") })

(def statichandler
   (-> handler
          (wrap-file (System/getProperty "user.dir"))
	         (wrap-file-info)))

;;
;; The react-pixi javascript files are stored in the react-pixi jar,
;; so we need the handler to serve those files properly. For any other
;; request the handler will rely on standard static file serving.
;;

(defroutes reactpixifromjar
  (resources "/react-pixi" {:root "react_pixi"})
  (files "/" {:root "(dev-resources/public|resources/public)"}))


