(ns todo-app.core
  (:require [ring.adapter.jetty :as jetty]
            [todo-app.handler :refer [app]]))

(defn -main []
  (jetty/run-jetty app {:port 3000}))
