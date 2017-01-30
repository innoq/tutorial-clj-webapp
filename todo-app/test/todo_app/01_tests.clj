(ns todo-app.01-tests
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [ring.mock.request :as mock]
            [todo-app.handler :refer [page app]]))

(deftest test-page
  (testing "page function"
    (let [html (page "Title!" 
                 [:div "Hi!"] 
                 [:ul (for [n [1 2 3]] [:li n])])]
      (is (str/includes? html "<title>Title!</title>"))
      (is (str/includes? html "<div>Hi!</div>"))
      (is (str/includes? html "<ul><li>1</li><li>2</li><li>3</li></ul>")))))

(deftest styles-loaded
  (testing "css stylesheet linked"
    (let [html (page "Some title")]
      (is (str/includes? html "<link href=\"splendor.css\" rel=\"stylesheet\" type=\"text/css\">")))))

(deftest test-app
  (testing "main route"
    (let [response (app (mock/request :get "/"))]
      (is (= (:status response) 200))
      (is (str/includes? (:body response) "<title>TODO App</title>"))
      (is (str/includes? (:body response) "<h1>TODO App</h1>"))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))
