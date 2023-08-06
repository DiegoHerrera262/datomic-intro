(ns app.backend.resolvers.user
  (:require
    [app.backend.database.setup :refer [conn]]
    [app.backend.database.queries.user :as uq]
    [com.wsscode.pathom.connect :as pc]))

(comment
  (uq/query-user-by-id conn "953b6f7c-5746-4c5a-ac44-89f9cd747d47")
  (uq/query-users-by-role conn :owner)
  )

(pc/defresolver user-by-id [_ {:user/keys [id]}]
                {::pc/input  #{:user/id}
                 ::pc/output [:user/id :user/name :user/role :user/email]}
                (uq/query-user-by-id conn id))

(pc/defresolver user-tasks-by-id [_ {:user/keys [id]}]
                {::pc/input #{:user/id}
                 ::pc/output [:user/tasks]}
                {:user/tasks (uq/query-tasks-by-user conn id)})

(pc/defresolver user-by-role [_ {:user/keys [role]}]
                {::pc/input  #{:user/role}
                 ::pc/output [:list/role :list/items]}
                {:list/role role
                 :list/items (uq/query-users-by-role conn role)})

(def resolvers [user-by-id
                user-by-role
                user-tasks-by-id])