(ns {{name}}.api.server
  (:require [{{name}}.api.page :as page]
            [{{name}}.routes :refer [api-routes]]
            [aleph.http :as http]
            [bidi.ring :as br]
            [ring.util.response :refer [response]]
            [taoensso.timbre :as log]
            [wiring.core :as w]))

(defn handlers [config]
  {:status (fn [req]
             (response "ok"))})

(defn server [config]
  (log/info "Starting server")
  (let [server (http/start-server (some-fn (br/make-handler api-routes (handlers config))
                                           (page/ui-handler config))
                                  {:port 3000})]
    (w/->Component server
                   (fn []
                     (log/info "Stopping server")
                     (.close server)))))
