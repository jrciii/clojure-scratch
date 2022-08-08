(ns clojure-scratch.pipeline
  (:require [java-time :as jt])
  (:require [cheshire.core :refer :all]))

(defrecord Step [name func])

(defn runStep
  "Run a Step in a Pipeline"
  [input step]
  (let [{name :name
         func :func} step
        ts (jt/format "yyyyMMddhhmmssSSS" (jt/local-date-time))]
    (with-open [w (clojure.java.io/writer (str name "_output_" ts ".json") :append true)
                e (clojure.java.io/writer (str name "_exception_" ts ".json") :append true)]
      (map
       #(try
          (.write w (generate-string (func %)))
          (catch Exception ex (.write w (generate-string {:record %}))))
       input))))

; add index for better filenames
(defn pipe
  "Write results and failures of each step in sequence, passing result on each step into next step."
  ([input steps]
   (if (empty? steps)
     input
     (recur (runStep input (first steps)) (rest steps)))))

(def addOneStep (Step. "AddOne" (+ 1)))

(pipe [1] [addOneStep addOneStep])
