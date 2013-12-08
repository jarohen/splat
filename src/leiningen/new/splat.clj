(ns leiningen.new.splat
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]))

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
             ["target/resources/js/.ph" (render "ph")]
             ["target/generated/clj/.ph" (render "ph")]
             ["target/generated/cljs/.ph" (render "ph")]
             ["src/cljx/.ph" (render "ph")]
             ["resources/{{name}}-config.edn" (render "resources/config.edn" data)]
             
             ["src/clojure/{{sanitized}}/handler.clj" (render "clj/handler.clj" data)]
             ["src/cljs/{{sanitized}}/cljs/app.cljs" (render "cljs/app.cljs" data)]
             ["src/cljs/{{sanitized}}/cljs/home.cljs" (render "cljs/home.cljs" data)]
             ["externs/jquery.js" (render "externs/jquery.js")]))
  (println "Created!")
  (println "You can run the application with `lein dev`"))
