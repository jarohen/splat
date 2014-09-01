(ns leiningen.new.splat
  (:require [clojure.java.io :as io]
            [leiningen.new.templates :refer [renderer name-to-path ->files]]))

(def render (renderer "splat"))

(defn splat
  "Create a new CLJS Single Page Application"
  [name]
  (println "Creating a new CLJS Single Page Application...")
  (let [data {:name name
              :sanitized (name-to-path name)}]
    (->files data
             ["project.clj" (render "project.clj" data)]
             [".gitignore" (render "gitignore" data)]
             ["resources/{{name}}-config.edn" (render "resources/config.edn" data)]
             
             ["src/{{sanitized}}/service/handler.clj" (render "clj/handler.clj" data)]
             ["src/{{sanitized}}/service/css.clj" (render "clj/css.clj" data)]
             ["ui-src/{{sanitized}}/ui/app.cljs" (render "cljs/app.cljs" data)]
             ["externs/jquery.js" (render "externs/jquery.js")]
             "common-src"))
  (println "Created!")
  (println "To start the application, run `lein dev`, and then go to http://localhost:3000"))
