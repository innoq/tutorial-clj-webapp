# Creating Domain Objects

In Clojure, all data types are immutable. When adding to a collection, a new collection is returned. As stated in [the last section](/01_instructions.md), this uses [persistent data structures](https://en.wikipedia.org/wiki/Persistent_data_structure) so that the data structures are still efficient. 

This is done because a value in the real world does not and cannot change: the value `42` will always be `42`. 

However, when we are dealing with the real world we have the concept of **identity** which is where we associate a logical entity with a series of values which may change over time. For instance, we may associate the entity _mouse_ with the positions `x,y` that our computer mouse takes on while we move it around our screen.

This logical entity will have exactly one state at any given point of time.

An in depth explanation can be found [here](https://clojure.org/about/state).

In Clojure, we can use atoms to model this concept of identity. For instance, we can define a logical entity 'counter' whose state will be incremented over time.

    (def counter (atom 0))

The value of the counter can be read at any time by _dereferencing_ the counter denoted by the `@` symbol.

    @counter ;; => Retrieve current value of counter.

The value of the atom is changed by applying a function which takes the current value of the atom and returns the new value. This function can be applied with the `swap!` function. In the case of our counter atom, we want some function which takes an integer and increments it... I know! lets just use the built in function `inc`!

    (swap! counter inc) ;; => updates state of counter and returns new value

In Clojure the `!` is used to denote a function which has a side effect.

## Task 02:

We have defined the namespace `src/todo_app/domain.clj` which contains the stubs of the functions needed to define the domain model for this application. To complete the task, modify the functions so that the tests pass!

We first need to decide how we want to model our application state (hint: use an atom!). The todo items themselves should have the following form:

    {:id 3 :text "Some text!"}

The id of the function should be unique. 

Create a `gen-id` function which generates a unique id. The tests for the application assume that this id is an integer.

Create a function `add-todo!` which adds an item to the list and then returns the new item.

Create a function `remove-todo!` which takes an item id and removes the correct item from the list.

Create a function `all-todos` which returns a collection containing all of the todo elements.

Create a function `todo-by-id` which retrieves a specific todo by its id.

Hint: use the [Clojure Cheatsheet](https://clojure.org/api/cheatsheet) to see what functions can be used for dealing with maps, vectors, and keywords!

You've hopefully either executed the `next` script or copied the `tests/02_tests.clj` file over to your project. Once these tests pass, move on to the next task!

If you need some help, look at the `cheats/02/domain.clj` file.
