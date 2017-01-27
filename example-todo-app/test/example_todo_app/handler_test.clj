(ns example-todo-app.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [example-todo-app.domain :refer [counter todos]]
            [example-todo-app.handler :refer :all]
            [hiccup.form :as f]))

(deftest test-page
  (testing "html template"
    (let [html (page "HI!" ":)")]
      (is (.contains html "<title>HI!</title>"))
      (is (.contains html ":)")))))

(deftest test-app
  (testing "main route"
    (reset! todos [{:id 0 :text "Buy Milk!"} {:id 1 :text "Buy Pizza!"}])
    (let [response (app (mock/request :get "/"))]
      (is (= (:status response) 200))
      (is (.contains (:body response) "<h1>TODO App</h1><ul><li><a href=\"/0\">Buy Milk!</a></li><li><a href=\"/1\">Buy Pizza!</a></li></ul>"))))
  
  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))

(deftest test-add-route 
  (testing "add route"
    (let [response (app-routes (mock/request :post "/" {:todo "Hi!"}))]
      (is (= (:status response) 302))
      (is (= (:headers response) {"Location" "/"}))))
  
  (testing "Do not add if todo empty"
    (let [oldtodos @todos
          response (app-routes (mock/request :post "/" {:todo ""}))]
      (is (= oldtodos @todos)))))

(deftest test-show
  (testing "show route"
    (reset! todos [{:id 1 :text "Buy Milk!"}])
    (let [response (app (mock/request :get "/1"))]
       (is (:status response) 200)
       (is (.contains (:body response) "Buy Milk!")))))
  
(deftest test-delete
  (testing "delete route"
    (reset! todos [{:id 1 :text "Buy Milk!"}])
    (let [response (app-routes (mock/request :delete "/1"))]
      (is (= @todos []))
      (is (:status response) 302)
      (is (= (:headers response) {"Location" "/"})))))