(ns chat-bot.core-test
  (:require [clojure.test :refer :all]
            [chat-bot.core :refer :all])
  (:import (chat_bot LocalTestServer)))

(defn with-neo4j-server*
  [f & args]
  (let [server (LocalTestServer.)]
    (try
      (.start server)
      (apply f args)
      (finally
        (.stop server)))))

(defmacro with-neo4j-server
  [& body]
  `(with-neo4j-server* (fn [] ~@body)))

(deftest a-test
  (testing "FIXME, I fail."
    (with-neo4j-server
      (let [conn (create-connection "http://localhost:7474/db/data/")]
        (create-token-constraint conn)
        (add-line "this is a test." conn)
        (is (= 4 (token-count conn)))
        (is (= 3 (link-count conn)))))))
