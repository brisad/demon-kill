(defproject demon-kill "0.1.0-SNAPSHOT"
  :description "Guess the word before the demon kills you"
  :url "http://github.com/brisad/demon-kill"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]]
  :main ^:skip-aot demon-kill.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
