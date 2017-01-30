(ns tutorial.handler-test
  (:require [clojure.test :refer :all]
            [clojure.string :as str]
            [ring.mock.request :as mock]
            [tutorial.handler :refer :all]))

(deftest test-app
  (testing "main route"
    (let [response (app (mock/request :get "/"))]
      (is (= (:status response) 200))
      (is (str/includes? (:body response) "<h1>Clojure Tutorial</h1>"))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))
