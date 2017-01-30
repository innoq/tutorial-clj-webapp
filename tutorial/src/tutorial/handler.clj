(ns tutorial.handler
  (:require [compojure.core :refer [defroutes GET]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [hiccup.page :refer [html5 include-css]]
            [hiccup.element :refer [link-to]]
            [markdown.core :refer [md-to-html-string]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defn load-chapters []
  (let [chapter-dir (io/file "chapters")
        chapters    (filter #(.isFile %) (file-seq chapter-dir))]
    (sort-by :name 
      (mapv (fn [f] {:name (.getName f) :text (slurp f)}) chapters))))

(defn page [title & content]
  (html5 
    [:head 
     [:title title]
     (include-css "splendor.min.css")]      
    [:body content]))

(defn first-line [text]
  (re-find #"(?im)^.*$" text))

(defn link-name [text]
  (let [line (first-line text)]
    (str/replace line #"^# " "")))

(defn index []
  (page 
    "Clojure Tutorial"
    [:h1 "Clojure Tutorial"]
    [:ul
      (for [chapter (load-chapters)]
           [:li (link-to
                  (:name chapter)
                  (link-name (:text chapter)))])]))

(defn show [chapter-name]
  (let [[chapter next] (drop-while #(not= chapter-name (:name %)) (load-chapters))]
    (when chapter
      (page
        (:name chapter)
        [:p (link-to "/" "Back to Tutorial")
            (md-to-html-string (:text chapter))]
        (when next
          [:p (link-to
                (:name next)
                (str "Next: " (link-name (:text next))))])))))

(defroutes app-routes
  (GET "/" [] (index))
  (GET "/:chapter" [chapter] (show chapter))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
