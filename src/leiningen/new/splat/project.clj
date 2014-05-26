(defproject {{name}} ""

  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  
  :dependencies [[org.clojure/clojure "1.6.0"]

                 [ring/ring-core "1.2.0"]
                 [compojure "1.1.6"]
                 [hiccup "1.0.5"]

                 [prismatic/dommy "0.1.2"]

                 [org.clojure/clojurescript "0.0-2202"]
                 [org.clojure/core.async "0.1.278.0-76b25b-alpha"]

                 ;; TODO temporary until CLJX doesn't insist on piggieback 0.1.0
                 [com.cemerick/piggieback "0.1.3"]
                 [com.keminglabs/cljx "0.3.2" :exclusions [com.cemerick/piggieback]]]

  :plugins [[jarohen/lein-frodo "0.3.0"]
            [jarohen/simple-brepl "0.1.0"]
            [lein-cljsbuild "1.0.3"]
            [lein-pdo "0.1.1"]

            [com.keminglabs/cljx "0.3.2" :middleware false]
            [lein-shell "0.4.0"]]

  ;; TODO temporary until CLJX doesn't insist on piggieback 0.1.0
  :repl-options {:nrepl-middleware [cljx.repl-middleware/wrap-cljx cemerick.piggieback/wrap-cljs-repl]}

  :frodo/config-resource "{{name}}-config.edn"

  :source-paths ["src" "target/generated/clj"]

  :resource-paths ["resources" "target/resources"]

  :cljx {:builds [{:source-paths ["src"]
                   :output-path "target/generated/clj"
                   :rules :clj}

                  {:source-paths ["src"]
                   :output-path "target/generated/cljs"
                   :rules :cljs}]}

  :cljsbuild {:builds {:dev
                       {:source-paths ["src" "target/generated/cljs"]
                        :compiler {:output-to "target/resources/js/{{name}}.js"
                                   :output-dir "target/resources/js/"
                                   :optimizations :whitespace
                                   :pretty-print true

                                   ;; uncomment for source-maps
                                        ; :source-map "target/resources/js/{{name}}.js.map"
                                   }}

                       :prod
                       {:source-paths ["src" "target/generated/cljs"]
                        :compiler {:output-to "target/resources/js/{{name}}.js"
                                   :optimizations :advanced
                                   :pretty-print false
                                   :externs ["externs/jquery.js"]}}}}

  :aliases {"dev" ["do"
                   ["shell" "mkdir" "-p"
                    "target/generated/clj"
                    "target/generated/cljs"
                    "target/resources"]
                   ["cljx" "once"]
                   ["pdo"
                    ["cljx" "auto"]
                    ["cljsbuild" "auto" "dev"]
                    "frodo"]]
            
            "start" ["do"
                     ["cljx" "once"]
                     ["cljsbuild" "once" "prod"]
                     ["trampoline" "frodo"]]})
