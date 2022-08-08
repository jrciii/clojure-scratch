(ns clojure-scratch.pipeline
  (:require [java-time :as jt])
  (:require [cheshire.core :refer :all]))

(defrecord Step [name func])

(defn runStep
  "Run a Step in a Pipeline"
  [input step index]
  (let [{name :name
         func :func} step
        ts (jt/format "yyyyMMddhhmmssSSS" (jt/local-date-time))]
    (with-open [w (clojure.java.io/writer (str name "_" index "_output_" ts ".json") :append true)
                e (clojure.java.io/writer (str name "_" index "_exception_" ts ".json") :append true)]
      (doall (map
              #(try
                 (let [result (func %)]
                   (.write w (str (generate-string result) "\n"))
                   result)
                 (catch Exception ex (.write e (str (generate-string {:exception (.getMessage ex) :record %}) "\n"))))
              input)))))

(defn pipe
  "Write results and failures of each step in sequence, passing result on each step into next step."
  ([input steps] (pipe input steps 0))
  ([input steps index]
   (if (empty? steps)
     input
     (recur (runStep input (first steps) index) (rest steps) (inc index)))))

(def addOneStep (Step. "AddOne" #(+ 1 %)))

(pipe [1] [addOneStep addOneStep])
