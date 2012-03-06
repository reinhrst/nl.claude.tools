(ns nl.claude.tools.conversion)

(defn- number-to-byte-array-be [number nrbytes]
  "will transform any number into a Byte[] --- note: some extra code is needed to support BigInt"
  (byte-array
    (map #(.byteValue %)
         (map bit-shift-right
              (repeat number)
              (map (partial * 8) (reverse (range nrbytes)))))))

(defn int-to-byte-array-be [integer]
  "converts the integer to a big-endian byte array"
  (number-to-byte-array-be integer 4))

(defn long-to-byte-array-be [long]
  "converts (signed) long to big endian byte array"
  (number-to-byte-array-be long 8))
