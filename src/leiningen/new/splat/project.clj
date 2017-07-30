(defproject {{name}} ""
  :dependencies [[org.clojure/clojure "1.8.0"]

                 [org.clojure/tools.nrepl "0.2.13"]
                 [cider/cider-nrepl "0.14.0"]
                 [refactor-nrepl "2.3.0"]

                 [com.taoensso/timbre "4.10.0"]

                 [cheshire "5.7.1"]

                 [aleph "0.4.3"]
                 [ring/ring-core "1.6.1"]
                 [ring/ring-defaults "0.3.0"]
                 [ring-middleware-format "0.7.2"]
                 [rm-hull/ring-gzip-middleware "0.1.7"]
                 [bidi "2.1.1"]
                 [hiccup "1.0.5"]

                 [jarohen/wiring "0.0.1-alpha1"]]

  :exclusions [org.clojure/clojurescript]

  :source-paths ["src/clj" "src/cljc" "src/cljs"]

  :plugins [[cider/cider-nrepl "0.14.0"]
            [lein-pdo "0.1.1"]
            [lein-shell "0.4.1"]]

  :profiles {:cljs {:dependencies [[org.clojure/clojurescript "1.9.562"]
                                   [figwheel-sidecar "0.5.10"]

                                   [reagent "0.7.0" :exclude [cljsjs/react]]
                                   [cljsjs/react-dom "15.4.2-2"]
                                   [cljsjs/react-with-addons "15.4.2-2"]
                                   [jarohen/oak "0.0.1-20170607.122106-4"]

                                   [cljs-http "0.1.43"]]

                    :exclusions [cljsjs/react
                                 cljsjs/react-dom
                                 cljsjs/react-dom-router
                                 cljsjs/react-dom-server]}

             :dev [:cljs]}

  :uberjar-name "{{name}}-standalone.jar"
  :auto-clean false
  :filespecs [{:type :paths, :paths ["target/dist"]}]
  :jar-exclusions [#"^public/s/static/js/deps/"]

  :aliases {"dev-api" ["run" "-m" "{{name}}.api.main"]
            "dev-ui" ["with-profiles" "+cljs" "run" "-m" "{{name}}.api.figwheel/start-figwheel!"]
            "build-ui" ["with-profiles" "+cljs" "run" "-m" "{{name}}.api.figwheel/build-ui!"]

            ;; npm install -g less less-plugin-clean-css
            "build-less" ["shell" "lessc" "--clean-css" "dev-resources/public/s/static/less/main.less" "target/dist/public/s/static/css/site.css"]

            "build" ["do"
                     ["clean"]
                     ["pdo" "compile," "build-ui," "build-less"]
                     ["uberjar"]]})
