(ns app.backend.database.queries.user
  (:require
    [app.backend.database.queries.shared :as shared]
    [datomic.client.api :as d]))

(defn query-user-by-id
  "Fetch user data by id"
  [conn id]
  (shared/remap-id (d/pull
                     (d/db conn)
                     '[:db/id
                       :user/name
                       :user/role
                       :user/email]
                     id) :user/id))

(defn query-users-by-role
  "Fetch all users with given role"
  [conn role]
  (let [db (d/db conn)
        query '[:find (pull ?e pattern)
                :in $ ?role pattern
                :where
                [?e :user/role ?role]]
        result (d/q query db role '[:db/id
                                    :user/name
                                    :user/email
                                    :user/role])]
    (mapv first result)))

(defn query-tasks-by-user
  "Fetch all tasks for given user"
  [conn user-id]
  (let [db (d/db conn)
        query '[:find (pull ?t pattern)
                :in $ ?user-id pattern
                :where
                [?t :task/assignee ?user-id]]
        result (d/q query db user-id '[:db/id])]
    (println result)
    (->> result
         (mapv (comp #(shared/remap-id % :task/id) first)))))
