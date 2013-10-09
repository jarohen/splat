(ns leiningen.new.spa-template
  (:use [leiningen.new.templates :only [renderer name-to-path ->files]]))

(def render (renderer "spa-template"))

(defn spa-template
  "Create a new CLJS Single Page Application"
  [name]
  (let [data {:name name
              :sanitized (name-to-path name)}]
    (->files data
             ["project.clj" (render "project.clj" data)]
             [".gitignore" (render "gitignore" data)]
             ["target/resources/js/.ph" ""])))
