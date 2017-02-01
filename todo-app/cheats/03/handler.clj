(ns todo-app.handler
  (:require [compojure.coercions :refer [as-int]]
            [compojure.core :refer [defroutes GET POST DELETE]]
            [compojure.route :as route]
            [hiccup.page :refer [html5 include-css]]
            [hiccup.form :as f]
            [hiccup.util :refer [escape-html]]
            [hiccup.element :refer [link-to]]
            [todo-app.domain :refer [all-todos todo-by-id add-todo! remove-todo!]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
            [ring.util.response :refer [redirect]]))

(defn page [title & body]
  (html5 
    [:head 
     [:title title]
     (include-css "splendor.css")]
    [:body [:h1 title] body]))

(defn index []
  (page "TODO App"
     [:ul
       (for [todo (all-todos)]
         [:li (link-to (str "/" (:id todo)) (:text todo))])]
     (f/form-to [:post "/"]
                (anti-forgery-field)
                (f/text-field "todo")
                (f/submit-button "Add"))))

(defn add-todo [todo]
  (when (not (clojure.string/blank? todo))    
    (add-todo! (escape-html todo)))
  (redirect "/"))

(defn show-todo [id]
  (let [todo (todo-by-id id)]
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