(ns {{name}}.service.handler
  (:require [{{name}}.service.css :as css]
            [ring.util.response :refer [response content-type]]
            [ring.middleware.format :refer [wrap-restful-format]]
            [clojure.java.io :as io]
            [compojure.core :refer [routes context GET]]
            [compojure.route :refer [resources]]
            [compojure.handler :refer [api]]
            [hiccup.page :refer [html5 include-css include-js]]
            [frodo.web :refer [App]]
            [simple-brepl.service :refer [brepl-js]]))

(defn page-frame []
  (html5
   [:head
    [:title "{{name}} - CLJS Single Page Web Application"]

    [:script (brepl-js)]

    (include-css "/css/site.css")
    
    (include-js "//cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js")
    (include-js "//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js")
    (include-css "//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css")
    
    (if-let [cljs-base (io/resource "js/goog/base.js")]
      (list (include-js "/js/goog/base.js")
            (include-js "/js/{{name}}.js")
            [:script "goog.require('{{sanitized}}.ui.app');"])
      
      (include-js "/js/{{name}}.js"))]
   
   [:body]))

(defn site-routes []
  (routes
    (GET "/" [] (response (page-frame)))

    (resources "/js" {:root "js"})
    (resources "/img" {:root "img"})
    
    (GET "/css/site.css" []
      (-> (response (css/site-css))
          (content-type "text/css")))))

(defn api-routes []
  (context "/api" []
    (-> (routes
          )

        (wrap-restful-format :formats [:edn :json-kw]))))

(def app
  (reify App
    (start! [_]
      {:frodo/handler (-> (routes
                            (site-routes)
                            (api-routes))
                          api)})
    (stop! [_ system])))
