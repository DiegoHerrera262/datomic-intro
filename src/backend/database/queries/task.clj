(ns backend.database.queries.task
  (:require [datomic.api :as d]))

(defn query-task-by-id
  "Fetch task data by uuid"
  [conn id]
  (d/pull
    (d/db conn)
    '[:db/id
      :task/id
      :task/description
      :task/status
      {:task/assignee [:user/id]}]
    [:task/id id]))

(defn query-task-id-by-uuid
  [conn id]
  (d/pull
    (d/db conn)
    '[:db/id]
    [:task/id id]))

(defn query-tasks-by-status
  "Fetch all tasks with given status"
  [conn status]
  (let [db (d/db conn)
        query '[:find (pull ?e pattern)
                :in $ ?status pattern
                :where
                [?e :task/status ?status]]
        result (d/q query db status '[:db/id :task/id :task/description :task/status :task/assignee])]
    (mapv first result)))
