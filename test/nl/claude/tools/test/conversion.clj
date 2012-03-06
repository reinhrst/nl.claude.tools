(ns nl.claude.tools.test.conversion
  (:use nl.claude.tools.conversion)
  (:use clojure.test))

(deftest test-int-to-byte-array-be
  (is (= (seq (int-to-byte-array-be 0x000F7FFF)) '(0 15 127 -1)) "simple case")
  (is (= (seq (int-to-byte-array-be 0xF00F7FFF)) '(-16 15 127 -1)) "uint case")
  (is (= (seq (int-to-byte-array-be -1)) '(-1 -1 -1 -1)) "-1 case")
  (is (= (seq (int-to-byte-array-be 0)) '(0 0 0 0)) "0 case")
  (is (= (seq (int-to-byte-array-be 0xFFFFFFF00000000)) '(0 0 0 0)) "overflow case"))
  