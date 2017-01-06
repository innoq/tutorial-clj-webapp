(ns example-todo-app.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [example-todo-app.handler :refer :all]))

(deftest test-page
  (testing "html template"
    (let [html (page "HI!")]
      (is (= html "<!DOCTYPE html>\n<html><head><title>HI!</title></head><body><h1>HI!</h1></body></html>")))))

(deftest test-app
  (testing "main route"
    (let [response (app (mock/request :get "/"))]
      (is (= (:status response) 200))
      (is (= (:body response) "<!DOCTYPE html>\n<html><head><title>TODO App</title></head><body><h1>TODO App</h1></body></html>"))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))
