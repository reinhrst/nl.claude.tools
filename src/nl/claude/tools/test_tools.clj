(ns nl.claude.tools.test-tools
  (:import java.util.Date))

(defmacro how-long-ms [& x]
  "Returns a veactor with first element the result of the body, and second element the number of miliseconds the body took"
  
  `(let [~'todo #(do ~@x)
         ~'start-time (.getTime (Date.))]
     [(~'todo) (- (.getTime (Date.)) ~'start-time)]))

