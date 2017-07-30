(ns {{name}}.ui.main
  (:require [{{name}}.routes :refer [app-routes]]
            [cemerick.url :as curl]
            [cljs.reader :as edn]
            [oak.core :as o]
            [oak.reagent :as r]
            [oak.nav :as nav]
            [oak.nav.bidi :as nav.bidi]))

(enable-console-print!)

(defn page-root [state]
  [:div
   "Hello world!"])

(defmulti handle-event o/dispatch-by-type)

(defmethod handle-event :nav [{old-location :location, :as state} {:keys [new-location] :as ev}]
  (-> state
      (assoc :location new-location)
      (nav/handle-nav {:old-location old-location, :new-location new-location})))

(defn ^:export main []
  (r/render! (-> {:$el (js/document.getElementById "app")

                  :->initial-state (fn [{:keys [location]}]
                                     (-> {:app {}
                                          :db {}
                                          :config (edn/read-string (-> (js/document.getElementById "config")
                                                                       (.getAttribute "value")))
                                          :location location}

                                         (nav/handle-mount {:location location})))

                  :handle-ev handle-event
                  :component (fn [state]
                               [page-root state])}

                 (nav/wrap-nav {:router (nav.bidi/->Router app-routes)
                                :change-ev (o/ev :nav)}))))
