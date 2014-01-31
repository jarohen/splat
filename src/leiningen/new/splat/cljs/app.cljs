(ns {{name}}.cljs.app
  (:require [dommy.core :as d]
            clojure.browser.repl)
  (:require-macros [dommy.macros :refer [node sel1]]))

(enable-console-print!)

(set! (.-onload js/window)
      (fn []
        (d/replace-contents! (sel1 :#content)
                             (node [:div.container
                                    [:h2 {:style {:margin-top :1em}}
                                     "Hello world from ClojureScript!"]]))))


