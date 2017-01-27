(ns example-todo-app.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [hiccup.page :refer [html5]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defn page [title]
  (html5 
    [:head [:title title]]
    [:body [:h1 title]]))

(defroutes app-routes
  (GET "/" [] (page "TODO App"))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
