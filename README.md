# A Tutorial for creating Web Applications in Clojure

## Prerequisites

The tutorial uses the Clojure build tool [leiningen](https://github.com/technomancy/leiningen), so this needs to be installed before running the tutorial.

Theoretically, you can use any text editor to create Clojure code. There are many diehard Emacs fans in the community.

However, for starting out with using Clojure, I recommend downloading [Nightcode](https://sekao.net/nightcode/) which provides a Clojure IDE with the basic functionality needed to get your app up and running. This IDE also comes built in with the [parinfer](https://shaunlebron.github.io/parinfer/) plugin to help with setting the parentheses correctly.

## How this tutorial is structured

The purpose of this tutorial is to help you build your first clojure web application. 

There are two subdirectories it this repository:

* `todo-app/` Contains the Clojure project that we are going to modify in the course of this tutorial
* `tutorial/` Contains the chapters of the tutorial in Markdown format. This can also be served as HTML as described in the next section

We've created the bare bones for a web application in the `todo-app` directory. 
During this tutorial, we will develop this application using test driven development. There are tests that have been created for each step in the tutorial to test if the task was correctly solved. These tests can be found in the `tests/` directory. If you want to retrieve these automatically, you can execute the `next` script when you want to retrieve the next tests. This will only be successful if the tests for the last task pass. ;)

If you get stuck, don't despair! We've created the directory `cheats/` which contains namespaces that you can copy into your project in order to get the tests to pass or to get an idea what you can do to fix your code. 

## The instructions for the tutorial.

The instructions for the tutorial can be found in `tutorial/chapters/`. 
These instructions are written in markdown, so they can be read in a text editor of your choice.
However, we have also written a small clojure web app to generate a website out of the markdown files so that it can be styled and is nicer to read. 

To start the web server:

        cd tutorial
        
Then start the server with:

        lein ring server 4000 

OR use the `run_tutorial` script which does this for you:

        ./run_tutorial 

The browser should pop open to the address [http://localhost:4000](http://localhost:4000) and you can see the tutorial

Once you have started the tutorial, move back into the project root (`cd ..`) and then get started modifying the `todo-app` project. 
