(ns todo-app.02-tests
  (:require [clojure.test :refer :all]
            [clojure.spec :as s]
            [todo-app.domain :refer [gen-id all-todos todo-by-id
                                     add-todo! remove-todo!]]))

(deftest test-gen-id
  (testing "we want to use an integer as an id"
    (is (integer? (gen-id))))
  
  (testing "unique id"
    (is (not= (gen-id) (gen-id)))))

(s/def ::id integer?)
(s/def ::text string?)
(s/def :unq/todo (s/keys :req-un [::id ::text]))

(deftest test-all-todos
  (testing "all-todos returns a collection"
    (is (coll? (all-todos))))

  (testing "todo elements conform to the specification {:id integer? :text string?}"
    (add-todo! "Make sure todos not empty!")
    (s/valid? (s/coll-of :unq/todo) (all-todos))))

(deftest test-get-by-id
  (testing "Retrieve by id"
    (let [todo (add-todo! "Some todo!")]
      (is (= (todo-by-id (:id todo)) todo)))))

(deftest test-add!
  (testing "adding todo"
    (let [oldcount (count (all-todos))
          newtodo  (add-todo! "Do sth cool!")]
      (is (= (count (all-todos)) (inc oldcount)))
      (is (= (todo-by-id (:id newtodo)) newtodo))
      (is (= (:text newtodo) "Do sth cool!")))))  

(deftest test-remove!
  (testing "removing todo"
    (let [oldtodos (all-todos)
          newtodo  (add-todo! "Do sth cool!")
          newtodos (remove-todo! (:id newtodo))]
      (is (= (all-todos) oldtodos)))))