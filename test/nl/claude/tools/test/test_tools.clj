(ns nl.claude.tools.test.test-tools
  (:use nl.claude.tools.test-tools)
  (:use clojure.test))

(deftest howlong
  (let [x (how-long-ms
           (Thread/sleep 300)
           42)]
    (is (< -1 (- (x 1) 300) 20) (str "Timing should be around 300 ms, was " (x 1)))
    (is (= 42 (x 0)) "Answer should have been 42")))