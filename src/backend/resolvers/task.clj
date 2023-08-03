(ns backend.resolvers.task
  (:require
    [backend.database.setup :refer [conn]]
    [backend.database.queries.task :as tq]
    [datomic.api :as d]
    [com.wsscode.pathom.connect :as pc]))

(comment
  (tq/query-task-by-id conn "88475611-2be9-459f-8192-e0aea42272d6")
  (tq/query-tasks-by-status conn :done)
  (d/pull
    (d/db conn)
    '[:task/description {:task/assignee [:user/id]}]
    [:task/id "9a344dfd-60ef-4a2e-b4a3-17c7387d2e5f"]))

(pc/defresolver task-by-id [_ {:task/keys [id]}]
                {::pc/input  #{:task/id}
                 ::pc/output [:task/id :task/description :task/status :task/assignee]}
                (tq/query-task-by-id conn id))

(pc/defresolver task-by-status [_ {:task/keys [status]}]
                {::pc/input  #{:task/status}
                 ::pc/output [:list/status :list/items]}
                {:list/status status
                 :list/items (tq/query-tasks-by-status conn status)})

(def resolvers [task-by-id
                task-by-status])