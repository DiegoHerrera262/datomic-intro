(ns backend.resolvers.task
  (:require
    [backend.database.setup :refer [conn]]
    [backend.database.queries.task :as tq]
    [com.wsscode.pathom.connect :as pc]))

(comment
  (tq/query-task-by-id conn "953b6f7c-5746-4c5a-ac44-89f9cd747d47")
  (tq/query-tasks-by-status conn :done)
  )

(pc/defresolver task-by-id [_ {:task/keys [id]}]
                {::pc/input  #{:task/id}
                 ::pc/output [:task/id :task/description :task/status]}
                (tq/query-task-by-id conn id))

(pc/defresolver task-by-status [_ {:task/keys [status]}]
                {::pc/input  #{:task/status}
                 ::pc/output [:list/status :list/items]}
                {:list/status status
                 :list/items (tq/query-tasks-by-status conn status)})

(def resolvers [task-by-id
                task-by-status])