(ns backend.mutations.user
  (:require
    [backend.database.setup :refer [conn]]
    [backend.database.queries.user :as uq]
    [com.wsscode.pathom.connect :as pc]
    [datomic.api :as d]))

(pc/defmutation create-user [_ {:user/keys [name role email]}]
                {::pc/sym    'create-user
                 ::pc/params [:user/name :user/role :user/email]
                 ::pc/output [:user/id :user/name :user/roles :user/email]}
                (let [user-id (str (java.util.UUID/randomUUID))]
                  @(d/transact conn [{:user/id    user-id
                                      :user/name  name
                                      :user/role  role
                                      :user/email email}])
                  {:user/id    user-id
                   :user/name  name
                   :user/role  role
                   :user/email email}))

(pc/defmutation update-user [_ {:user/keys [id name role email]}]
                {::pc/sym    'update-user
                 ::pc/params [:user/id :user/name :user/role :user/email]
                 ::pc/output [:user/id :user/name :user/role :user/email]}
                (let [prev-data (uq/query-user-by-id conn id)
                      user-entity-id (:db/id prev-data)
                      update-data {:user/name  (or name (:user/name prev-data))
                                   :user/role  (or role (:user/role prev-data))
                                   :user/email (or email (:user/email prev-data))}
                      update-transaction (assoc update-data :db/id user-entity-id)]
                  (println prev-data)
                  @(d/transact conn [update-transaction])
                  (assoc update-data :user/id id)))

(def mutations [create-user
                update-user])
