(defproject chat-bot "0.1.0-SNAPSHOT"
  :description "Chat bot using neo4j"
  :url "http://github.com/TronPaul/chat-bot"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [clojurewerkz/neocons "3.0.0"]]
  :main ^:skip-aot chat-bot.core
  :java-source-paths ["test/java"]
  :test-paths ["test/clj" "test/java"]
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :dev {:dependencies [[org.neo4j/neo4j-kernel "2.1.7" :classifier "tests"]
                                  [org.neo4j/neo4j-kernel "2.1.7"]
                                  [org.neo4j.app/neo4j-server "2.1.7"]]}})
