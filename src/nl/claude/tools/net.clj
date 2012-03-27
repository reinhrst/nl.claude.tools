(ns nl.claude.tools.net
  (:import java.net.ServerSocket))

(defn new-connections-sequence [port]
  "Opens the specified port. For each connection that is made, returns a Java.net.Socket"
  (let [server-socket (java.net.ServerSocket. port)]
    (letfn [(accept-loop []
              (lazy-seq
                (cons (.accept server-socket) (accept-loop))))]
            (accept-loop))))