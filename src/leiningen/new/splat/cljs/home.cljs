(ns {{name}}.cljs.home
  (:require [dommy.core :as d])
  (:require-macros [dommy.macros :refer [node sel1]]))

(defn watch-hash! [!hash]
  (add-watch !hash :home-page
             (fn [_ _ _ hash]
               (when (= "#/" hash)
                 (d/replace-contents! (sel1 :#content) (node [:h2 {:style {:margin-top :1em}}
                                                              "Hello world from ClojureScript!"]))))))
