(ns {{name}}.api.main
  (:require [wiring.core :as w]
            [clojure.java.io :as io]
            [clojure.tools.nrepl.server :as nrepl]
            [clojure.tools.reader.edn :as edn]
            [cider.nrepl :as cider]
            [refactor-nrepl.middleware :refer [wrap-refactor]]
            [taoensso.timbre :as log]))

(w/defsystem api
  {:web-server '{{name}}.api.server/server

   :wiring/secret-keys (edn/read-string (slurp (or (System/getenv "WIRING_SECRETS")
                                                   (io/file "secrets.edn"))))})

(defn -main [& args]
  (nrepl/start-server :bind "127.0.0.1"
                      :port 7888
                      :handler (-> cider/cider-nrepl-handler
                                   wrap-refactor))

  (log/info "nREPL started, port 7888")

  (start-api!))
