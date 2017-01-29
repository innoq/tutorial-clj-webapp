(ns todo-app.03-tests
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [todo-app.domain :refer [todos]]
            [todo-app.handler :refer :all]
            [hiccup.form :as f]))

(deftest test-index
  (testing "index"
    (reset! todos [{:id 0 :text "Buy Milk!"} {:id 1 :text "Buy Pizza!"}])
    (let [response (app (mock/request :get "/"))]
      (is (= (:status response) 200))
      (is (.contains (:body response) "<h1>TODO App</h1><ul><li><a href=\"/0\">Buy Milk!</a></li><li><a href=\"/1\">Buy Pizza!</a></li></ul>")))))

(deftest test-add-route 
  (testing "add route"
    (reset! todos [])
    (let [response (app-routes (-> (mock/request :post "/")
                                   (assoc :params {:todo "Hi!"})))]
      (is (= (:status response) 302))
      (is (= (:headers response) {"Location" "/"}))
      (is (= (count @todos) 1))))
  
  (testing "Do not add if todo empty"
    (reset! todos [])
    (let [response (app-routes (-> (mock/request :post "/")
                                   (assoc :params {:todo ""})))]
      (is (= (count @todos) 0))))
  
  (testing "Escape todo if evil!"
    (let [response (app-routes (-> (mock/request :post "/")
                                   (assoc :params {:todo "<script>alert('hi!')</script>"})))]
      (is (= (:text (last @todos)) "&lt;script&gt;alert(&apos;hi!&apos;)&lt;/script&gt;")))))

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