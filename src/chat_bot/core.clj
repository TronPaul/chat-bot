(ns chat-bot.core
  (:require [clojurewerkz.neocons.rest :as nr]
            [clojurewerkz.neocons.rest.nodes :as nn]
            [clojurewerkz.neocons.rest.relationships :as nrl]
            [clojurewerkz.neocons.rest.constraints :as nc]
            [clojurewerkz.neocons.rest.labels :as nl]
            [clojurewerkz.neocons.rest.cypher :as cy]
            [clojure.string :refer [split]])
  (:gen-class))

(defn create-connection
  [url]
  (nr/connect url))

(def token-label
  "Token")

(defn create-token-constraint
  [conn]
  (nc/create-unique conn token-label :text))

(defn- tokenize
  [line]
  (split line #" "))

(defn- create-token
  [text conn]
  (let [token (nn/create conn {:text text})]
    (nl/add conn token token-label)
    token))

(defn- create-tokens
  [tokens conn]
  (map #(create-token % conn) tokens))

(defn- create-link
  [[n1 n2] conn]
  (nrl/create conn n1 n2 :link {:weight 1}))

(defn- create-chain
  [nodes conn]
  (map #(create-link % conn) (partition 2 1 nodes)))

(defn add-line
  [line conn]
  (let [str-tokens (tokenize line)
        tokens (create-tokens str-tokens conn)]
    (doall (create-chain tokens conn))))

(defn token-count
  [conn]
  (count (nl/get-all-nodes conn token-label)))

(defn link-count
  [conn]
  (-> (:data (cy/query conn "MATCH (n:Token)-[r]->() RETURN count(*)"))
      (nth 0)
      (nth 0)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "nothin"))
