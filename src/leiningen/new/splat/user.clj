(ns user
  (:require [{{name}}.api.main :as main]))

(defn stop-system! []
  (main/stop-api!))

(defn start-system! []
  (main/start-api!))

(defn current-system []
  @main/!api-system)
