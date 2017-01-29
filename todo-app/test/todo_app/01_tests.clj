(ns todo-app.01-tests
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [todo-app.handler :refer :all]))

(deftest test-page
  (testing "page function"
    (let [html (page "Title!" 
                 [:div "Hi!"] 
                 [:ul (for [n [1 2 3]] [:li n])])]
      (is (.contains html "<title>Title!</title>"))
      (is (.contains html "<div>Hi!</div>"))
      (is (.contains html "<ul><li>1</li><li>2</li><li>3</li></ul>")))))

(deftest styles-loaded
  (testing "css stylesheet linked"
    (let [html (page "Some title")]
      (is (.contains html "<link href=\"splendor.css\" rel=\"stylesheet\" type=\"text/css\">")))))

(deftest test-app
  (testing "main route"
    (let [response (app (mock/request :get "/"))]
      (is (= (:status response) 200))
      (is (.contains (:body response) "<title>TODO App</title>"))
      (is (.contains (:body response) "<h1>TODO App</h1>"))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))
