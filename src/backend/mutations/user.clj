(ns backend.mutations.user
  (:require
    [backend.database.setup :refer [conn]]
    [com.wsscode.pathom.connect :as pc]
    [datomic.api :as d]))

(pc/defmutation create-user [_ {:user/keys [name role email]}]
  {::pc/sym    'create-user
   ::pc/params [:user/name :user/role :user/email]
   ::pc/output [:user/id :user/name :user/roles :user/email]}
  (let [user-id (str (java.util.UUID/randomUUID))
        result @(d/transact conn [{:user/id    user-id
                                   :user/name  name
                                   :user/role  role
                                   :user/email email}])]
    (println result)
    {:user/id    user-id
     :user/name  name
     :user/role  role
     :user/email email})
  )

(pc/defmutation update-user [_ {:user/keys [id name role email]}]
  {::pc/sym    'update-user
   ::pc/params [:user/id :user/name :user/role :user/email]
   ::pc/output [:user/id :user/name :user/role :user/email]}
  (let [db (d/db conn)
        query '[:find ?entity ?name ?role ?email
                :in $ ?user-id
                :where
                [?entity :user/id ?user-id]
                [?entity :user/name ?name]
                [?entity :user/role ?role]
                [?entity :user/email ?email]]
        result (d/q query db id)
        current (first result)
        user-entity-id (nth current 0)
        user-prev-name (nth current 1)
        user-prev-role (nth current 2)
        user-prev-email (nth current 3)
        update-data {:user/name  (or name user-prev-name)
                     :user/role  (or role user-prev-role)
                     :user/email (or email user-prev-email)}
        update-transaction (assoc update-data :db/id user-entity-id)]
    (println current)
    @(d/transact conn [update-transaction])
    (assoc update-data :user/id id)))

(def mutations [create-user
                update-user])
