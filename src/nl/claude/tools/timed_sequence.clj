(ns nl.claude.tools.timed-sequence
  (:import java.util.Date))

(defn timed-sequence
 "Takes a sequence, returns a lazy sequence where each next element is only released after an interval. Note that counting is always done from start-time, not from the time of the last item that was requested.
  This can be used to simulate a buffered stream where items are added in regular intervals. Reading a not-available item results in blocking until it's availble. Note that seconds-between-items may be
  any positive numeric."
 ([sequence seconds-between-items]
    (let [miliseconds-between-items (* 1000 seconds-between-items)]
      (letfn ([worker [working-sequence start-time item-number]
               (let [next-item-available (+ start-time (* item-number miliseconds-between-items))
                     wait-time-ms (- next-item-available (.getTime (Date.)))]
                 (when-let [item (first working-sequence)]
                   (do
                     (when (pos? wait-time-ms) (Thread/sleep wait-time-ms))
                     (lazy-seq (cons item (worker (rest working-sequence) start-time (inc item-number)))))))])
        (worker sequence (.getTime (Date.)) 0)))))
