# Chapter 03
## Routing

We now have our HTML templates and our domain objects. Now we want to tie everything together. 

The library we are using for routing in our application is [Compojure](https://github.com/weavejester/compojure).

We've seen the most basic route definition already.

`(GET "/" [] "HI!")`

If we want to extract a parameter from the URI, Compojure gives us the opportunity to destructure it.

`(GET "/:book" [book] (show book))`

This also works for form parameters

`(POST "/" [something] (dosth something))`

More information about the destructuring syntax can be found [here](https://github.com/weavejester/compojure/wiki/Destructuring-Syntax).

To generate redirects, the function `ring.util.response/redirect` can be used.

To generate forms, use the functions available from `hiccup.form`

## Middleware & CSRF

In Clojure, the requests and responses are translated into Clojure data structures which are modelled as Clojure maps. This according to the [Ring standard](https://github.com/ring-clojure/ring/wiki/Concepts), which is used in all major Clojure webservers. 

This gives you complete control over the requests and responses. Once pattern is to write middleware which can modify a request going in or modify a request when going out.

```
(defn middleware [handler]
   (fn [request]
      ;; Do something to request
      (let [response (handler request)]
         ;; Do something to response
         response)))
```

It is not necessary to write a lot of middleware when just getting started. The default template for Compojure uses the default middleware `site-defaults` which is available from the `ring.middleware.defaults`. 

Among other things, the middleware initializes the [CSRF Protection](https://www.owasp.org/index.php/Cross-Site_Request_Forgery_(CSRF\)) that is available in Clojure. Clojure protects against Anti-Forgery attacks by rendering a secret into the HTML of a web page. With requests which modify the application state (i.e. :post, :put, :delete, :patch), the client needs to send this secret back to the server so that the server knows that it comes from a valid user. This secret can be added into your application by generating a `ring.util.anti-forgery/anti-forgery-field` within all of the necessary forms in the application.

Task 03:

Define the following routes for the application

1. GET / -> returns a list of todos
2. POST / -> adds a new todo, redirects to /
3. GET /:id -> shows a single todo
4. DELETE /:id -> removes todo, redirects to /

The tests can be seen in the `handler_test.clj` file

Now you have a working web application!

As a bonus task, you can make it pretty by adding CSS!