(ns {{name}}.api.figwheel
  (:require [figwheel-sidecar.system :as fig]
            [cljs.build.api :as cljs]))

(def source-paths
  ["src/cljs" "src/cljc"])

(defn start-figwheel! []
  (fig/start-figwheel! {:all-builds [{:id :dev
                                      :source-paths source-paths
                                      :compiler {:main '{{name}}.ui.main
                                                 :output-dir "target/dev/public/static/js/deps"
                                                 :output-to "target/dev/public/static/js/app.js"
                                                 :asset-path "/static/js/deps"}
                                      :figwheel {:on-jsload 'reagent.core/force-update-all}}]
                        :build-ids [:dev]
                        :figwheel-options {:server-logfile false}}))


(defn build-ui! []
  (cljs/build (apply cljs/inputs source-paths)
              {:infer-externs true
               :output-dir "target/dist/public/static/js/deps"
               :optimizations :advanced
               :recompile-dependents false
               :parallel-build true
               :output-to "target/dist/public/static/js/app.js"
               :output-wrapper true
               :pseudo-names false
               :compiler-stats true
               :main '{{name}}.ui.main}))
