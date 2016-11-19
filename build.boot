(set-env!
  :source-paths #{"src"}
  :resource-paths #{"resources"}
  :dependencies '[[org.clojure/clojure "1.7.0"]
                  [ring/ring-jetty-adapter "1.4.0"]
                  [compojure "1.4.0"]
                  [io.aviso/rook "0.1.39"]
                  [cheshire "5.6.3"]
                  [org.clojure/tools.logging "0.2.6"]
                  [org.slf4j/slf4j-log4j12 "1.7.12"]
                  [log4j/log4j "1.2.17"]
                  [org.clojure/core.memoize "0.5.6"]
                  [clojail "1.0.6"]])

(deftask build
         "Builds the application uberjar."
         []
         (comp (aot :all true)
               (uber)
               (jar :main 'chromeclojure.server
                    :file "chromeclojure.jar")
               (sift :include #{#"chromeclojure.jar" #"example.policy"})
               (target :dir #{"target"})))

(deftask run
         "Runs the application server"
         []
         (require 'chromeclojure.server)
         (let [runner (resolve 'chromeclojure.server/-main)]
           (runner))
         (wait))

(deftask idea
         "Updates pom.xml for Idea to pick up dependency changes."
         []
         (comp (pom :project 'chromeclojure
                    :version "0.0.1-SNAPSHOT"
                    :description "A Clojure Plugin for Google Chrome.")
               (sift :move {#"META-INF/maven/chromeclojure/chromeclojure/pom.xml" "pom.xml"}
                     :include #{#"pom.xml"})
               (target :dir #{"."}
                       :no-clean true)))