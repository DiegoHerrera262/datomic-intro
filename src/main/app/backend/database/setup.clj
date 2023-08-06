(ns app.backend.database.setup
  (:require
    [app.backend.database.schemas.task :refer [task-schema]]
    [app.backend.database.schemas.user :refer [user-schema]]
    [datomic.client.api :as d]))

;; Create a db connection for local usage
(def db-config {:server-type        :peer-server
                :access-key         "task-key"
                :secret             "task-secret"
                :endpoint           "localhost:8998"
                :validate-hostnames false})
(def client (d/client db-config))
(def conn (d/connect client {:db-name "task-app"}))

;; Transact the app schemas
(def transacted-attributes (d/transact conn {:tx-data (concat
                                                         task-schema
                                                         user-schema)}))
