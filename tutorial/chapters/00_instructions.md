# First Steps

If you are reading this tutorial, the chances that you have downloaded the correct git archive are quite high. 

Congratulations!

If you are viewing this tutorial in a browser, the chances that you have already installed [leinigen](http://github.com/technomancy/leinigen) are also quite high. If this is not the case, please do this right now.

During this tutorial we want to have a hands on experience with Clojure and build a Clojure web application from the base up.

The app that we are going to be working on is the `todo-app` (so if you `'cd tutorial'`ed to start this tutorial, `cd ../todo-app` to get into the correct directory now). From now on, we will assume you are working in this directory. 
 
The app was created using the compojure leinigen template

    lein new compojure todo-app

If you are using the [Nightcode IDE](https://sekao.net/nightcode/) for developing Clojure (which I recommend, especially when starting out with the language), you can start the application directly from the IDE.

Otherwise, you can start the app from the command line:

    lein ring server

By default, this will start a server on port 3000 (the port can also be optionally specified as an additional argument)

For each task in the tutorial, we have created a test file to test if the the task has been fulfilled. The first test file can already be found in the project test files: `/test/todo_app/01_tests.clj`

The tests can be run as follows:

    lein test

Further tests for each task can be found in the `tests/` directory. If you are stuck on a specific task, don't despair! There is a `cheats/` directory which contains an example namespace that you can copy over to your project source files (`src/todo_app`) so that the tests will pass. 

If you think that you have finished one of the tasks, you can execute the `next` script which will check if all tests pass, and if so, will copy over the next test file.

There are no tests for this step in the tutorial, so go on to the next step!
