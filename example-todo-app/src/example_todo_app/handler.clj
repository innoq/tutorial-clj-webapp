(ns example-todo-app.handler
  (:require [compojure.coercions :refer [as-int]]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [hiccup.page :refer [html5 include-css]]
            [hiccup.form :as f]
            [hiccup.element :refer [link-to]]
            [example-todo-app.domain :refer [todos add-todo! remove-todo!]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
            [ring.util.response :refer [redirect]]))

(defn page [title & body]
  (html5 
    [:head 
     [:title title]
     (include-css "splendor.min.css" "style.css")]
    [:body [:h1 title] body]))

(defn index []
  (page "TODO App"
     [:ul
       (for [todo @todos]
         [:li (link-to (str "/" (:id todo)) (:text todo))])]
     (f/form-to [:post "/"]
                (anti-forgery-field)
                (f/text-field "todo")
                (f/submit-button "Add"))))

(defn add-todo [todo]
  (when (not (clojure.string/blank? todo))
    (add-todo! todo))
  (redirect "/"))

(defn show-todo [id]
  (let [todo (first (filter #(= id (:id %)) @todos))]
    (when todo 
      (page (str "TODO " id) 
        [:h2 (:text todo)]
        (f/form-to [:delete (str "/" id)]
                (anti-forgery-field)
                (f/submit-button "Delete"))))))

(defn delete-todo [id]
  (remove-todo! id)
  (redirect "/"))

(defroutes app-routes
  (GET  "/" [] (index))
  (POST "/" [todo] (add-todo todo))
  (GET  "/:id" [id :<< as-int] (show-todo id))
  (DELETE "/:id" [id :<< as-int] (delete-todo id))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
