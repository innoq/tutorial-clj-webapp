(ns tutorial.core
  (:require [ring.adapter.jetty :as jetty]
            [tutorial.handler :refer [app]]))

(defn -main []
  (jetty/run-jetty app {:port 4000}))
