(ns backend.database.queries.user
  (:require [datomic.api :as d]))

(defn query-user-by-id
  "Fetch user data by uuid"
  [conn id]
  (let [db (d/db conn)
        query '[:find (pull ?e pattern)
                :in $ ?user-id pattern
                :where
                [?e :user/id ?user-id]]
        result (d/q query db id '[:db/id :user/id :user/name :user/email :user/role])]
    (ffirst result)))

(defn query-users-by-role
  "Fetch all users with given role"
  [conn role]
  (let [db (d/db conn)
        query '[:find (pull ?e pattern)
                :in $ ?role pattern
                :where
                [?e :user/role ?role]]
        result (d/q query db role '[:db/id :user/id :user/name :user/email :user/role])]
    (mapv first result)))