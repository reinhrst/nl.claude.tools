(ns nl.claude.tools.dct)

(defn dct-ii [data]
  "Does one-dimensional DCT-II transform, with a scaling factor. For more info see http://en.wikipedia.org/wiki/Discrete_Math/cosine_transform#DCT-II"
  (let [N (count data)]
    (map
      (fn [k]
        (*
          (apply
            +
            (map
              (fn [n xn]
                (* xn (Math/cos (/ (* Math/PI (+ n 1/2) k) N))))
              (range)
              data))
          (Math/sqrt (/ (if (zero? k) 1 2) N)))) ;scaling factor; for X0 scale by Math/sqrt(1/N), for others Math/sqrt(2/N)
      (range N))))

(defn dct-iii [data]
  "One dimensional inverse DCT (DCT-III). As far as I could see, the function on http://en.wikipedia.org/wiki/Discrete_Math/cosine_transform#DCT-III contains an error, so the implemented function is from http://planetmath.org/encyclopedia/DCTIII.html"
  (let [N (count data), scaled-x0 (/ (first data) (Math/sqrt 2))]
    (map
      (fn [k]
        (*
            (apply
              +
              (map
                (fn [n xn]
                  (* 
                    (if (zero? n) scaled-x0 xn)
                    (Math/cos (/ (* Math/PI n (+ k 1/2)) N))))
                (range)
                data))
          (Math/sqrt (/ 2 N)))) ;scaling factor;
      (range N))))

(def dct dct-ii)
(def idct dct-iii)
