(ns backend.resolvers.user
  (:require
    [backend.database.setup :refer [conn]]
    [datomic.api :as d]
    [com.wsscode.pathom.connect :as pc]))

(defn parse-user-result [attrs-vector]
  {:user/id    (nth attrs-vector 0)
   :user/name  (nth attrs-vector 1)
   :user/role  (nth attrs-vector 2)
   :user/email (nth attrs-vector 3)})

(pc/defresolver user-by-id [_ {:user/keys [id]}]
  {::pc/input  #{:user/id}
   ::pc/output [:user/id :user/name :user/role :user/email]}
  (let [db (d/db conn)
        query '[:find ?user-id ?name ?role ?email
                :in $ ?user-id
                :where
                [?e :user/id ?user-id]
                [?e :user/name ?name]
                [?e :user/role ?role]
                [?e :user/email ?email]]
        result (d/q query db id)
        current (first result)]
    (println current)
    (parse-user-result current)))

(pc/defresolver user-by-role [_ {:list/keys [id]}]
  {::pc/input  #{:list/id}
   ::pc/output [:list/id :list/users]}
  (let [db (d/db conn)
        query '[:find ?user-id ?name ?role ?email
                :in $ ?role
                :where
                [?e :user/role ?role]
                [?e :user/id ?user-id]
                [?e :user/name ?name]
                [?e :user/email ?email]]
        result (d/q query db id)
        users-list (mapv parse-user-result (vec result))]
    {:list/id    id
     :list/users users-list}))

(def resolvers [user-by-id
                user-by-role])