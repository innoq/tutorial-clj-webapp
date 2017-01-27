# Tutorial Step 1

Install [leinigen](http://github.com/technomancy/leinigen) to build your clojure project.

Create a leinigen project for your todo app based on the compojure template.

`lein new compojure todo-app`

Update the `project.clj` so that it contains the latest version of the hiccup templating library (currently version "1.0.5")

The application can be started with the command:

`lein ring server`

By default, this will start a server on port 3000.

Try to modify the application so that it returns the string "Hello My Lovely World!"

Once you are done, execute the `./next` script to retrieve the next instruction step. 

If you don't know what to do, execute the `./next` script to retrieve an example application.

