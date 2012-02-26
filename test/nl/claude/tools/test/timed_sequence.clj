(ns nl.claude.tools.test.timed-sequence
  (:use [nl.claude.tools.timed-sequence])
  (:use [clojure.test])
  (:import java.util.Date))

(defmacro how-long-ms [& x]
  "Returns a veactor with first element the result of the body, and second element the number of miliseconds the body took"
  
  `(let [~'todo #(do ~@x)
         ~'start-time (.getTime (Date.))]
     [(~'todo) (- (.getTime (Date.)) ~'start-time)]))

(defmacro test-equal [sequence delay selector]
  `(is (= (~selector ~sequence) (doall (~selector (timed-sequence ~sequence ~delay)))) (str "expected sequences to be equal: (" '~selector '~sequence ") with delay " ~delay)))

(defmacro test-timing [sequence delay selector]
  `(let [~'result (how-long-ms (doall (~selector (timed-sequence ~sequence ~delay))))
         ~'expected-delay (* (count (~'result 0)) ~delay 1000) ; *1000 because we want the delay in ms
         ~'actual-delay (~'result 1)
         ~'delay-difference (- ~'actual-delay ~'expected-delay)]
     (is (< -1 ~'delay-difference 20) (str "The delay differce is not within limits; expected delay " ~'expected-delay " ms, actual delay " ~'actual-delay ". Note that because we're talking about timing here, other factors may influence the measurement (high load / slow computer)"))))

(defn test-all
  ([sequence] (test-all sequence 1/50))
  ([sequence delay] (test-all sequence delay identity))
  ([sequence delay selector]
     (do (test-equal sequence delay selector) (test-timing sequence delay selector))))

(deftest finite-sequence
  (test-all [1 2 3 4 5 6 7 8 9 10]))

(deftest finite-sequence-with-selector
  (test-all (range 10) 0.01 (partial take 2)))

(deftest infinite-sequence
  (test-all (range) 0.002 (partial take 200)))

(deftest infinite-sequence-sub-ms-timing
  (test-all (range) 1/3000 (partial take 2000)))

(deftest very-large-set
  (test-all (range) 1/100000 (partial take 100000)))

