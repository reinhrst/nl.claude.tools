(ns nl.claude.tools.test.timed-sequence
  (:use [nl.claude.tools.timed-sequence])
  (:use [clojure.test])
  (:import java.util.Date))

(defmacro how-long-ms [& x]
  "Returns a veactor with first element the result of the body, and second element the number of miliseconds the body took"
  
  `(let [~'todo #(do ~@x)
         ~'start-time (.getTime (Date.))]
     [(~'todo) (- (.getTime (Date.)) ~'start-time)]))

(deftest sequence-test
  (let [test-sequence [1 2 3 4 5 6 7 8 9 10]
        delay 0.1]
    (let [result (how-long-ms (doall (timed-sequence test-sequence delay)))]
      (is (= (result 0) test-sequence) "timed-sequence should not alter the sequence")
      (is (<= 0 (- (result 1) (* 1000 delay (dec (count test-sequence)))) 20) "We don't want more than 20 ms overhead")) ;after the last one it's announcd directly that we're done
    (let [result (how-long-ms (doall (take 2 (timed-sequence test-sequence delay))))]
      (is (= (result 0) (take 2 test-sequence)) "take before or after timing should be equal")
      (is (<= 0 (- (result 1) (* 1000 delay 2)) 20) "We don't want more than 20 ms overhead when taking")))
  (let [test-sequence (range)
        delay 0.1]
    (let [result (how-long-ms (doall (take 20 (timed-sequence test-sequence delay))))]
      (is (= (result 0) (take 20 test-sequence)) "should work with infinite sequence")
      (is (<= 0 (- (result 1) (* 1000 delay 20)) 20) "infinity sequence should still take the right amount of time"))
    (let [result (how-long-ms (doall (take 2000 (timed-sequence test-sequence 1/3000))))]
      (is (= (result 0) (take 2000 test-sequence)) "should work with very long sequence")
      (is (<= 0 (- (result 1) (* 1000 1/3000 2000)) 20) "even long sequence with small sleep should work (if fails, maybe computer is too slow?)"))))