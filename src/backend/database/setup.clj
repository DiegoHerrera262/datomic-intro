(ns backend.database.setup
  (:require
    [backend.database.schemas.task :refer [task-schema]]
    [backend.database.schemas.user :refer [user-schema]]
    [datomic.api :as d]))

;; Create a db connection for local usage
(def db-uri "datomic:mem://datomic-intro")
(d/create-database db-uri)
(def conn (d/connect db-uri))

;; Transact the app schemas
(def transacted-attributes @(d/transact conn (concat
                   task-schema
                   user-schema)))
