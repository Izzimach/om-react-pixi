(defproject org.clojars.haussman/om-react-pixi "0.4.0"
  :description "ClojureScript definitions for using om with react-pixi"
  :url "https://github.com/Izzimach/om-react-pixi"
  :license {:name "Apache 2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0.html"}

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2843"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [org.omcljs/om "0.8.8" :exclusions [cljsjs/react]]
                 [org.clojars.haussman/react-pixi "0.4.0"]
                 [prismatic/schema "0.3.7"]
                 [prismatic/om-tools "0.3.10"]]

  :profiles {:dev {:dependencies [[ring/ring-core "1.2.2"]
                                  [figwheel "0.2.5-SNAPSHOT"]
                                  [ring/ring-jetty-adapter "1.2.2"]
                                  [compojure "1.3.1"]]
                   :source-paths ["src" "dev-src"]}}

  :plugins [[lein-cljsbuild "1.0.5"]
            [lein-figwheel "0.2.5-SNAPSHOT"]
            [lein-ring "0.8.10"]
            [com.cemerick/clojurescript.test "0.3.3"]]

  :ring {:port 8081}

  :aliases {"autotest" ["do" "clean," "cljsbuild" "auto" "test"]}

  :figwheel {
             :http-server-root "public"
             :server-port 8081
             :ring-handler webserver.servefromjar/reactpixifromjar
             }

  :cljsbuild {
              ;; lein cljsbuild test
              :test-commands {
                              "unit" ["node" "node_modules/slimerjs/bin/slimerjs"
                                      :runner
                                      "dev-resources/private/out/tests.js"]}

              :builds [
                       {
                        :id "hello"
                        :source-paths ["src/omreactpixi" "dev-src/examples/hello"]
                        :compiler {
                                   :output-to "dev-resources/public/examples/hello/out/hello.js"
                                   :output-dir "dev-resources/public/examples/hello/out"
                                   :source-map true
                                   :optimizations :none}}
                       {
                        :id "dev"
                        :source-paths ["src/omreactpixi"]
                        :compiler {
                                   :output-to "out/om-react-pixi.js"
                                   :output-dir "out"
                                   :optimizations :none
                                   :source-map true}}
                       {
                        :id "packed"
                        :source-paths ["src/omreactpixi"]
                        :compiler {
                                   :output-to "out-min/om-react-pixi.min.js"
                                   :output-dir "out-min"
                                   :optimizations :advanced
                                   ;; the react_pixi dir is found inside the react-pixi jar
                                   :preamble ["react_pixi/react-pixi.min.js"]
                                   :externs ["react_pixi/react-pixi.js"]
                                   :closure-warnings {:externs-validation :off
                                                      :non-standard-jsdoc :off}}}
                       {
                        :id "cupcake"
                        :source-paths ["src/omreactpixi" "dev-src/examples/cupcake"]
                        :compiler {
                                   :output-to "dev-resources/public/examples/cupcake/out/cupcake.js"
                                   :output-dir "dev-resources/public/examples/cupcake/out"
                                   :optimizations :none
                                   :source-map true}}
                       {
                        :id "cupcake-min"
                        :source-paths ["src/omreactpixi" "dev-src/examples/cupcake"]
                        :compiler {
                                   :output-to "dev-resources/public/examples/cupcake/out-min/cupcake.js"
                                   :output-dir "dev-resources/public/examples/cupcake/out-min"
                                   :externs ["react_pixi/react-pixi.min.js" "react_pixi/pixi.dev.js"]
                                   :optimizations :advanced
                                   :pretty-print false
                                   :closure-warnings {:externs-validation :off
                                                      :non-standard-jsdoc :off}}}
                       {
                        :id "preloader"
                        :source-paths ["src/omreactpixi" "dev-src/examples/preloader"]
                        :compiler {
                                   :output-to "dev-resources/public/examples/preloader/out/preloader.js"
                                   :output-dir "dev-resources/public/examples/preloader/out"
                                   :source-map true
                                   :optimizations :none}}
                       {
                        :id "interactive"
                        :source-paths ["src/omreactpixi" "dev-src/examples/interactive"]
                        :compiler {
                                   :output-to "dev-resources/public/examples/interactive/out/interactive.js"
                                   :output-dir "dev-resources/public/examples/interactive/out"
                                   :source-map true
                                   :optimizations :none}}
                       {
                        :id "test"
                        :source-paths ["src/omreactpixi" "dev-src/tests"]
                        ;; auto-run tests after a rebuild
                        ;; so you can run 'lein cljsbuild auto test'
                        :notify-command ["node" "node_modules/slimerjs/bin/slimerjs"
                                         :cljs.test/runner
                                         "dev-resources/private/out/tests.js"]
                        :compiler {
                                   :output-to "dev-resources/private/out/tests.js"
                                   :output-dir "dev-resources/private/out"
                                   :preamble ["react_pixi/pixi.dev.js" "react_pixi/react-pixi.js"]
                                   :optimizations :simple
                                   :pretty-print true}}]})
