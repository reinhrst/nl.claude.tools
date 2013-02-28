(ns nl.claude.tools.test.dct
  (:use [nl.claude.tools.dct])
  (:use [clojure.test]))

(defn compareresults [s1 s2]
  "returns true if the results of the two lists are 'close enough'"
  (> 0.0001 (apply
              +
              (map
                (fn [x y] (* (- x y) (- x y)))
                s1
                s2))))

(def TESTDATA ; first is the PCM data, second the DCT'ed data
  {
   [1 1 1 1] [2 0 0 0]
   [1 2 3 4] [5.0 -2.2304424973876635 0.0 -0.15851266778110737]
   [1 0 1 0 1] [1.3416407864998736 7.847188731329875E-17 0.39087901516970963 7.767078044146794E-18 1.0233345472033855]
   [1 0 0 0 0] [0.4472135954999579 0.6015009550075456 0.5116672736016927 0.3717480344601845 0.19543950758485482]
   [1.923879532511287 -0.38268343236508984 0.3826834323650896 0.07612046748871315] [1 1 1 1]
   [473.3349627693266 140.00782838932423 -129.00782838932423 -416.3349627693266] [34 654 23 65]
   [1 2 3 4 1 2 3 4 1 2 3 4 1 2 3 4 1 2 3 4 1 2 3 4] [12.24744871391589 -0.7257326521604486 2.6761948156555826E-17 -0.7600770840377169 7.784069401064276E-17 -0.8415401737027526 0.0 -1.0115297562955667 1.1176563983684992E-16 -1.428022339973081 7.788483792482202E-17 -3.517556245857869 0.0 2.697542573509587 -5.6728735930521E-17 0.5783819191425519 -1.0874394362743817E-17 0.0931248363277477 0.0 -0.21511253467132188 6.246197802025692E-17 -0.617934229348756 5.5386124601590645E-18 -2.168804499267645]
  })


(deftest DTC-II
  (loop [data (vec TESTDATA)]
    (when data
      (is (compareresults (dct ((first data) 0)) ((first data) 1)) (str "dct failed for" (first data)))
      (is (compareresults (dct-ii ((first data) 0)) ((first data) 1)) (str "dct-ii failed for" (first data)))
      (recur (next data)))))

(deftest DTC-III
  (loop [data (vec TESTDATA)]
    (when data
      (is (compareresults (idct ((first data) 1)) ((first data) 0)) (str "idct failed for" (first data)))
      (is (compareresults (dct-iii ((first data) 1)) ((first data) 0)) (str "dct-iii failed for" (first data)))
      (recur (next data)))))
      
