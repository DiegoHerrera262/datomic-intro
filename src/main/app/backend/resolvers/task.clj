(ns app.backend.resolvers.task
  (:require
    [app.backend.database.setup :refer [conn]]
    [app.backend.database.queries.task :as tq]
    [com.wsscode.pathom.connect :as pc]))

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