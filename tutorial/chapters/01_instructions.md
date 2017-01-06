# Chapter 01

Congratulations! You have created your first Clojure web application!

Currently, the application has one route '/' which returns a message in text format. 

We've created an example application `example-todo-app` which will illustrate our solution to the different tasks throughout the rest of the tutorial.  

## HTML Templating

We would like for this route to return an HTML page instead of using text. For this, we can use the Hiccup library which translates Clojure data structures into the html format.

In Clojure, one of the datatypes is a keyword. This is a symbolic identifier which stands for itself. A keyword looks like this:

    :keyword

    :anotherKeyword

Another important data type in Clojure are vectors. Vectors are a collection type that are similar in functionality to a list but are optimized to provide a quick lookup time for finding elements within the collection. A vector is denoted by square brackets `[` `]` and, because Clojure is a dynamic programming language, can contain elements of any type.

    [:I :am :a :vector :of :keywords :and [:a :subvector :of :keywords]]

Hiccup takes advantage of the fact that conceptually, an HTML element is a list of subelements which has a particular name, i.e.

    <div>
        <span>I am a subelement of this div!</span>
        <div>I am a subelement too!</div>
    </div>

In Clojure we have something like a list (Vectors!) and something like a name (Keywords!), so we can combine them to get a representation of an html page.

    [:body
       [:h1 "Title"]
       "Some content!"]

Hiccup provides the `hiccup.page/html5` element for generating an HTML5 page from such a data structure. This can also be combined with functions to have reusable templates!

    (defn page [title content]
       (html5
          [:head [:title title]]
          [:body [:h1 title]
             content]))

## Namespaces

In Clojure, functions are defined within a namespace. A new namespace can be created by creating a file `my_file.clj` within the src root of the project. Within this file, the namespace is defined with the `ns` macro and the namespace name, which is the path of the file with '-' instead of '_' and '.' instead of '/'

The file `src/example_web_app/my_file.clj` will therefore have the following namespace declaration.

    (ns example-web-app.my-file)

If you want to use these functions within another namespace, you need to explicitly require the namespace to load it

    (ns example.handler
       (:require [hiccup.page]))

Then functions from this namespace can be used by prefixing them with the namespace, i.e. `hiccup.page/html5`

If you want to use the function without the namespace prefix, you can refer them after requiring them.

    (ns example.handler
       (:require [hiccup.page :refer [html5]]))

Or you can just refer all of the functions from a namespace (only a good idea if you use a lot of them and know that there will be no name clashes with any other namespaces that you are loading):

    (ns example.handler
       (:require [hiccup.page :refer :all]))

Task 01:

Update your application to include a dependency to hiccup (currently in version `2.0.0-alpha1`). 
Create a `page` function which takes the title of a page and embeds it in a generated html document.
Use this function to produce HTML for the main route which sets the title of the page to `TODO App`.

Tests for this behavior can be found in the `handler_test.clj` file.
