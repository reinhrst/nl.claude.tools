(ns nl.claude.tools.test.conversion
  (:use nl.claude.tools.conversion)
  (:use clojure.test))


(defmacro conversion-test [f in out message mask]
  `(do
     (is (= (seq (~f ~in)) ~out) ~message)
     (is (= (byte-array-be-to-number ~out) (bit-and ~in ~mask)) (str "reverse-" ~message))))

(defmacro int-conversion-test [in out message]
  `(conversion-test int-to-byte-array-be ~in ~out ~message 0xFFFFFFFF))

(defmacro long-conversion-test [in out message]
  `(conversion-test long-to-byte-array-be ~in ~out ~message -1))



(deftest test-int-to-byte-array-be
  (int-conversion-test 0x000F7FFF '(0 15 127 -1) "simple case")
  (int-conversion-test 0xF00F7FFF '(-16 15 127 -1) "uint case")
  (int-conversion-test -1 '(-1 -1 -1 -1) "-1 case")
  (int-conversion-test 0 '(0 0 0 0) "0 case")
  (int-conversion-test 0xFFFFFFF00000000 '(0 0 0 0) "overflow case"))

(deftest test-long-to-byte-array-be
  (long-conversion-test 0x000F7FFF '(0 0 0 0 0 15 127 -1) "simple case")
  (long-conversion-test 0xF00F7FFF '(0 0 0 0 -16 15 127 -1) "uint case")
  (long-conversion-test -1 '(-1 -1 -1 -1 -1 -1 -1 -1) "-1 case")
  (long-conversion-test 0 '(0 0 0 0 0 0 0 0) "0 case")
  (long-conversion-test 0x7FFFFFFFFFFFFFFF '(127 -1 -1 -1 -1 -1 -1 -1) "overflow case"))
