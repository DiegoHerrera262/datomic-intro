(ns backend.database.schemas.task)

(def task-schema [{:db/ident :task/id
                   :db/valueType :db.type/string
                   :db/cardinality :db.cardinality/one
                   :db/unique :db.unique/identity
                   :db/doc "Task external ID"}

                  {:db/ident :task/description
                   :db/valueType :db.type/string
                   :db/cardinality :db.cardinality/one
                   :db/doc "Description of a task"}

                  {:db/ident :task/status
                   :db/valueType :db.type/keyword
                   :db/cardinality :db.cardinality/one
                   :db/doc "Status of a task [:pending :done]"}

                  {:db/ident :task/assignee
                   :db/valueType :db.type/ref
                   :db/cardinality :db.cardinality/one
                   :db/doc "User responsible of the task"}])