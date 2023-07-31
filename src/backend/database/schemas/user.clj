(ns backend.database.schemas.user)

(def user-schema [{:db/ident :user/name
                   :db/valueType :db.type/string
                   :db/cardinality :db.cardinality/one
                   :db/doc "User name"}

                  {:db/ident :user/role
                   :db/valueType :db.type/keyword
                   :db/cardinality :db.cardinality/many
                   :db/doc "User roles [:owner :crew :support :reporter]"}

                  {:db/ident :user/email
                   :db/valueType :db.type/string
                   :db/cardinality :db.cardinality/one
                   :db/doc "User email. used for authentication"}])