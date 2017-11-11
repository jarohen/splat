(ns {{name}}.api.page
  (:require [{{name}}.routes :refer [app-routes]]
            [bidi.bidi :as bidi]
            [bidi.ring :as br]
            [clojure.java.io :as io]
            [hiccup.core :as h]
            [hiccup.page :refer [html5 include-js include-css]]
            [ring.util.response :refer [response content-type charset]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.gzip :refer [wrap-gzip]]))

(defn page [req {:keys [dev-mode?] :as config}]
  (-> (response (html5
              [:head
               [:title "{{name}}"]
               [:meta#config {:value (pr-str {})}]
               (include-css "https://cdnjs.cloudflare.com/ajax/libs/normalize/7.0.0/normalize.min.css")
               (if-not dev-mode?
                 (include-css "/static/css/site.css")

                 (list
                  [:link {:rel "stylesheet/less"
                          :type "text/css"
                          :href "/static/less/main.less"
                          :data-env "development"}]

                  (include-js "https://cdnjs.cloudflare.com/ajax/libs/less.js/2.7.2/less.min.js")))]

              [:body
               [:div#app]
               (include-js "/static/js/app.js")
               [:script "{{sanitized}}.ui.main.main()"]]))

      (content-type "text/html")
      (charset "utf-8")))

(defn ui-handler [{:keys [dev-mode?] :as config}]
  (-> (br/make-handler app-routes (constantly #(page % config)))
      (wrap-defaults (-> site-defaults
                         (assoc-in [:responses :content-types] {:mime-types {"less" "text/css"}})
                         (assoc-in [:static :resources] "public")
                         (cond-> dev-mode? (assoc-in [:static :files] [(doto (io/file "target/dev/public")
                                                                         (.mkdirs))]))))
      (wrap-gzip)))
