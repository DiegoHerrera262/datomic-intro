(ns backend.database.queries.user
  (:require [datomic.api :as d]))

(defn query-user-by-id
  "Fetch user data by uuid"
  [conn id]
  (d/pull
    (d/db conn)
    '[:db/id
      :user/id
      :user/name
      :user/role
      :user/email]
    [:user/id id]))

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