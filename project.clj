(defproject clojure-scratch "0.1.0-SNAPSHOT"
  :description "Clojure Scratch"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [clojure.java-time "0.3.3"]
                 [cheshire "5.11.0"]]
  :main ^:skip-aot clojure-scratch.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
