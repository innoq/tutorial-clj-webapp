(ns todo-app.handler
  (:require [compojure.core :refer [defroutes GET POST DELETE]]
            [compojure.route :as route]
            [hiccup.page :refer [doctype include-css]]
            [hiccup2.core :refer [html]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defn page [title & content]
  (str
    (html
      (doctype :html5)
      [:html
       [:head
        [:title title]
        (include-css "splendor.css")]
       [:body content]])))

(defn index []
  (page "TODO App" 
    [:h1 "TODO App"]))

(defroutes app-routes
  (GET "/" [] (index))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
