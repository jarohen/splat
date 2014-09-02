(ns {{name}}.service.config
  (:require [clojure.java.io :as io]
            [nomad :refer [defconfig]]))

;; Call (config) to access your config file
(defconfig config (io/resource "{{name}}-config.edn"))

