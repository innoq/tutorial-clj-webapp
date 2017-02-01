(ns todo-app.domain)

(def ^:private counter (atom 0))
(def ^:private todos (atom []))

(defn gen-id [] (swap! counter inc))

(defn add-todo! [text]
  (let [new-todo {:id (gen-id) :text text}]
    (swap! todos conj new-todo)
    new-todo))

(defn remove-todo [todos id]
  (filterv #(not= id (:id %)) todos))

(defn remove-todo! [id]
  (swap! todos remove-todo id))

(defn all-todos [] @todos)

(defn todo-by-id [id]
  (first (filter #(= id (:id %)) @todos)))