# Basics, HTML Templating, and  Namespaces

If you have started the `todo-app` with `lein ring server` you can see what the app looks like at `http://localhost:3000`

The app currently only has a single route '/' which returns a message in text format. We want to return HTML instead of text, so let's look at what resources Clojure has to help us with this.

## Basics

Clojure is a [Lisp](https://en.wikipedia.org/wiki/Lisp_(programming_language\)) which runs on the [JVM](https://en.wikipedia.org/wiki/Java_virtual_machine), which means that elements that belong together are grouped together in parentheses `(` `)`.

As far as data types go, Clojure implements all of the basic data types, including integers:

    42

Strings:

    "I'm a string!"

Keywords (a symbolic identifier that stands for itself):

    :keyword

In Clojure, there is also great support for [persistent data structures](https://en.wikipedia.org/wiki/Persistent_data_structure) which are immutable by default and use structural sharing to remain efficient. Because Clojure is dynamically typed, they can contain a mixture of any data types. 

The most common collection types are lists: 

    (list 1 2 3)

Vectors (similar in functionality to lists, but optimized to provide a quick lookup time):

    [:I :am :a :vector :of :keywords :and [:a :subvector :of :keywords]]

Maps:

    {:someKey "Some Value" "String can be keys too" 2}

Commas are optional and are treated as whitespace.

As stated above, in Clojure all elements which belong together are grouped in parentheses. In addition to this, [prefix notation](https://en.wikipedia.org/wiki/Polish_notation) is exclusively used, so the function or [macro](https://clojure.org/reference/macros) which is being called is always found in the first position followed by all arguments.

A variable can be defined in Clojure with the `def` keyword

    (def x 5)

`def` should only be used for values which do not change!

A function can be defined with the keyword `fn`

    (fn [x] (+ x 5))

To define a function which has a specific name, these two can be combined with the `defn` keyword:

    (defn add2 [x y] (+ x y))

That's pretty much it as far as the syntax goes! Any additional syntax that we will need for our application will be introduced as we go.

The [Clojure Cheatsheet](https://clojure.org/api/cheatsheet) is a good reference for finding useful functions that can be used when developing Clojure.

For programming Clojure, it is a good idea to follow [this style guide](https://github.com/bbatsov/clojure-style-guide). This has the added benefit that if you are using the [parinfer plugin](https://shaunlebron.github.io/parinfer/) (which comes built in with Nightcode), by styling your Clojure code according to the style guide, the parentheses will be correctly generated and set.

## HTML Templating

We can use the [Hiccup](https://github.com/weavejester/hiccup) for doing HTML templating using Clojure data structures.

Hiccup takes advantage of the fact that conceptually, an HTML element is a list of subelements which has a particular name, i.e.

    <div>
        <span>I am a subelement of this div!</span>
        <div>I am a subelement too!</div>
    </div>

In Clojure we have something like a list (Vectors!) and something like a name (Keywords!), so we can combine them to get a representation of an HTML page.

    [:body
       [:h1 "Title"]
       "Some content!"]

Hiccup provides the `hiccup.page/html5` element for generating an HTML5 page from such a data structure. This can also be combined with functions to have reusable templates!

    (defn page [title & content]
       (html5
          [:head [:title title]]
          [:body content]))

The `&` in the function specifies that the function takes a variable number of arguments. The first argument `title` is required, and any others that are passed in will be bound to `content` as a list. In Hiccup, you can simply insert the list of elements, and these will be rendered correctly within the body tag.

## Namespaces

In Clojure, functions are defined within a namespace. A new namespace can be created by creating a file `my_file.clj` within the src root of the project. Within this file, the namespace is defined with the `ns` macro and the namespace name, which is the path of the file with '-' instead of '\_' and '.' instead of '/'

The file `src/example_web_app/my_file.clj` will therefore have the following namespace declaration.

    (ns example-web-app.my-file)

You can also use namespaces from different projects by updating the `project.clj` file in your project root to add the project as a dependency (i.e. to add the compojure library, add `[compojure "1.5.1"]` to the dependencies in the `project.clj`).

If you want to use these functions within another namespace, you need to explicitly require the namespace

    (ns example.handler
       (:require [hiccup.page]))

Then functions from this namespace can be used by prefixing them with the namespace, i.e. `hiccup.page/html5`

If you want to use the function without the namespace prefix, you can refer them after requiring them.

    (ns example.handler
       (:require [hiccup.page :refer [html5]]))

Or you can just refer all of the functions from a namespace (only a good idea if you use a lot of them and know that there will be no name clashes with any other namespaces that you are requiring):

    (ns example.handler
       (:require [hiccup.page :refer :all]))

## Task 01:

Update your application by modifiying the `project.clj` file to include a dependency to hiccup (currently in version `2.0.0-alpha1`).

In the `src/todo_app/handler.clj` file, modify the `page` function so that it sets the title of the page and adds a variable number of elements to the body of the page. Also include the 'splendor.css' file that is a static resource in the project (found under `resources/public`) to the HTML page (HINT: use the `hiccup.page/include-css` function).

Use this function to produce HTML for the main route "/" which sets the title of the page to `TODO App`.

Tests for this task are already present in the `/test/todo_app/01_tests.clj` file. 

If you think you are done, execute the `next` script which will run the tests and, if successful, will copy the tests for Task 2 into the project. Then go on to the next step!

If you need some help, look at the `project.clj` and `handler.clj` files in the `cheats/01/` directory.
