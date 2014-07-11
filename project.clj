(defproject om-react-pixi "0.1.0-EXPERIMENTAL"
  :description "ClojureScript definitions for using om with react-pixi"
  :url "http://example.com/FIXME"
  :license {:name "Apache 2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0.html"}
  :plugins [[lein-cljsbuild "1.0.2"]]

  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-2202"]
                 [org.clojure/core.async "0.1.267.0-0d7780-alpha"]
                 [ring/ring-core "1.2.2"]
                 [ring/ring-jetty-adapter "1.2.2"]
                 [om "0.6.4"]]

  :plugins [[lein-cljsbuild "1.0.3"]
            [lein-ring "0.8.10"]]

  :ring {:handler webserver.core/statichandler :port 8081 }


  :cljsbuild {
              :builds [{:id "dev"
                        :source-paths ["src/omreactpixi"]
                        :compiler {
                                   :output-to "out/om-react-pixi.js"
                                   :output-dir "out"
                                   :optimizations :none
                                   :source-map true}}
                       {:id "packed"
                        ;; vendor is added to the source paths so that
                        ;; :preamble can find react-pixi.js
                        :source-paths ["src/omreactpixi" "vendor"]
                        :compiler {
                                   :output-to "out-min/om-react-pixi.min.js"
                                   :output-dir "out-min"
                                   :optimizations :advanced
                                   :preamble ["react-pixi.min.js"]
                                   :externs ["react-pixi.js"]
                                   :closure-warnings {:externs-validation :off
                                                      :non-standard-jsdoc :off}}}
                       {:id "hello"
                        :source-paths ["src/omreactpixi" "examples/hello/src"]
                        :compiler {
                                   :output-to "examples/hello/out/hello.js"
                                   :output-dir "examples/hello/out"
                                   :source-map true
                                   :optimizations :none}}
                       {:id "cupcake"
                        :source-paths ["src/omreactpixi" "examples/cupcake/src"]
                        :compiler {
                                   :output-to "examples/cupcake/out/cupcake.js"
                                   :output-dir "examples/cupcake/out"
                                   :source-map true
                                   :optimizations :none}}
                       {:id "preloader"
                        :source-paths ["src/omreactpixi" "examples/preloader/src"]
                        :compiler {
                                   :output-to "examples/preloader/out/preloader.js"
                                   :output-dir "examples/preloader/out"
                                   :source-map true
                                   :optimizations :none}}]})

