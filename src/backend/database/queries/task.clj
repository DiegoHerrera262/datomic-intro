(ns backend.database.queries.task
  (:require [datomic.api :as d]))

(defn query-task-by-id
  "Fetch task data by uuid"
  [conn id]
  (let [db (d/db conn)
        query '[:find (pull ?e pattern)
                :in $ ?task-id pattern
                :where
                [?e :task/id ?task-id]]
        result (d/q query db id '[:db/id :task/id :task/description :task/status])]
    (ffirst result)))

(defn query-tasks-by-status
  "Fetch all tasks with given status"
  [conn status]
  (let [db (d/db conn)
        query '[:find (pull ?e pattern)
                :in $ ?status pattern
                :where
                [?e :task/status ?status]]
        result (d/q query db status '[:db/id :task/id :task/description :task/status])]
    (mapv first result)))