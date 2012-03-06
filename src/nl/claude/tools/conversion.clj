(ns nl.claude.tools.conversion)

(defn int-to-byte-array-be [integer]
  "converts the integer to a big-endian byte array"
  (byte-array
    (map #(.byteValue %)
         (map bit-shift-right
              (repeat integer)
              [24 16 8 0]))))
  