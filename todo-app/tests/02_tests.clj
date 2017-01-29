(ns todo-app.02-tests
  (:require [clojure.test :refer :all]
            [todo-app.domain :refer :all]))

(deftest test-gen-id
  (testing "unique id"
    (is (not= (gen-id) (gen-id)))))

(deftest test-add!
  (testing "adding todo"
    (let [newtodo  (add-todo! "Do sth cool!")]
      (is (= (last @todos) newtodo))
      (is (= (:text newtodo) "Do sth cool!")))))  

(deftest test-remove!
  (testing "removing todo"
    (let [oldtodos @todos
          newtodo  (add-todo! "Do sth cool!")
          newtodos (remove-todo! (:id newtodo))]
      (is (= @todos oldtodos)))))