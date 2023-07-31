(ns backend.database.schemas.task)

(def task-schema [{:db/ident :task/description
                   :db/valueType :db.type/string
                   :db/cardinality :db.cardinality/one
                   :db/doc "Description of a task"}

                  {:db/ident :task/status
                   :db/valueType :db.type/string
                   :db/cardinality :db.cardinality/one
                   :db/doc "Status of a task (PENDING/DONE)"}])