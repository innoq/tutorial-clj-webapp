(ns todo-app.02-tests
  (:require [clojure.test :refer :all]
            [todo-app.domain :refer [gen-id todos
                                     add-todo! remove-todo!]]))

(deftest test-gen-id
  (testing "we want to use an integer as an id"
    (is (integer? (gen-id))))
  
  (testing "unique id"
    (is (not= (gen-id) (gen-id)))))

(deftest todos-is-an-atom
  (testing "todos is an atom"
    (instance? clojure.lang.Atom todos))
  
  (testing "todos wraps a vector"
    (vector? @todos)))

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