(ns todo-app.03-tests
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [hiccup.core :refer [html]]
            [hiccup.element :refer [link-to]]
            [ring.mock.request :as mock]
            [todo-app.domain :refer [all-todos todo-by-id add-todo!]]
            [todo-app.handler :refer [app app-routes]]
            [hiccup.form :as f]))

(deftest test-index
  (testing "index"
    (let [todos (all-todos)
          response (app (mock/request :get "/"))
          expected-html (html [:ul (for [todo todos] [:li (link-to (str "/" (:id todo)) (:text todo))])])]
      (is (= (:status response) 200))
      (is (str/includes? (:body response) "<h1>TODO App</h1>"))
      (is (str/includes? (:body response) expected-html)))))

(deftest test-add-route 
  (testing "add route"
    (let [oldcount (count (all-todos))
          response (app-routes (-> (mock/request :post "/")
                                   (assoc :params {:todo "Hi!"})))]
      (is (= (:status response) 302))
      (is (= (:headers response) {"Location" "/"}))
      (is (= (count (all-todos)) (inc oldcount)))))
  
  (testing "Do not add if todo empty"
    (let [oldtodos (all-todos)
          response (app-routes (-> (mock/request :post "/")
                                   (assoc :params {:todo ""})))]
      (is (= (all-todos) oldtodos))))
  
  (testing "Escape todo if evil!"
    (let [response (app-routes (-> (mock/request :post "/")
                                   (assoc :params {:todo "<script>alert('hi!')</script>"})))]
      (is (not-any? #(str/includes? % "<script>") (all-todos))))))

(deftest test-show
  (testing "show route"
    (add-todo! "Make sure not empty!")
    (let [first    (first (all-todos))
          response (app (mock/request :get (str "/" (:id first))))]
       (is (:status response) 200)
       (is (str/includes? (:body response) (:text first))))))
  
(deftest test-delete
  (testing "delete route"
    (let [oldtodos (all-todos)
          todo     (add-todo! "To Delete")
          response (app-routes (mock/request :delete (str "/" (:id todo))))]
      (is (= (all-todos) oldtodos))
      (is (:status response) 302)
      (is (= (:headers response) {"Location" "/"})))))