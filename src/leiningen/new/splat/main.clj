(ns {{name}}.api.main
  (:require [wiring.core :as w]
            [clojure.java.io :as io]
            [clojure.tools.nrepl.server :as nrepl]
            [clojure.tools.reader.edn :as edn]
            [cider.nrepl :as cider]
            [refactor-nrepl.middleware :refer [wrap-refactor]]
            [taoensso.timbre :as log]))

(w/defsystem api
  ;; TODO move this to thing.api.server ns, and make wiring look it up there
  {:web-server {:wiring/component '{{name}}.api.server/server
                :dev-mode? true
                :wiring/switches {:built {:dev-mode? false}}}

   :wiring/secret-keys (edn/read-string (slurp (io/file "secrets.edn")))})

(defn -main [& args]
  (nrepl/start-server :bind "127.0.0.1"
                      :port 7888
                      :handler (-> cider/cider-nrepl-handler
                                   wrap-refactor))

  (log/info "nREPL started, port 7888")

  (start-api!))
