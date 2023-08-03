(ns backend.mutations.task
  (:require
    [backend.database.setup :refer [conn]]
    [backend.database.queries.task :as tq]
    [com.wsscode.pathom.connect :as pc]
    [datomic.api :as d]))

(pc/defmutation create-task [_ {:task/keys [description assignee]}]
                {::pc/sym    'create-task
                 ::pc/params [:task/description :task/assignee]
                 ::pc/output [:task/id :task/description :task/status :task/assignee]}
                (let [create-data {:task/id          (str (java.util.UUID/randomUUID))
                                   :task/description description
                                   :task/status      :pending
                                   :task/assignee    assignee}]
                  @(d/transact conn [create-data])
                  create-data))

(pc/defmutation update-task [_ {:task/keys [id description status assignee]}]
                {::pc/sym    'update-task
                 ::pc/params [:task/id :task/description :task/status :task/assignee]
                 ::pc/output [:task/id :task/description :task/status :task/assignee]}
                (let [prev-data (tq/query-task-by-id conn id)
                      update-data {:task/description (or description (:task/description prev-data))
                                   :task/status      (or status (:task/status prev-data))
                                   :task/assignee    (or assignee (:task/assignee prev-data))}
                      update-transaction (assoc update-data :db/id (:db/id prev-data))]
                  (if-not (nil? assignee)
                    @(d/transact conn [{:db/id         (:db/id prev-data)
                                        :task/assignee :db/retract}]))
                  @(d/transact conn [update-transaction])
                  (assoc update-data :task/id id)))

(pc/defmutation delete-task [_ {:task/keys [id]}]
                {::pc/sym    'delete-task
                 ::pc/params [:task/id]
                 ::pc/output [:task/id]}
                (let [entity-id (:db/id (tq/query-task-id-by-uuid conn id))]
                  @(d/transact conn [{:db/id            entity-id
                                      :db/retractEntity true}])
                  {:task/id id}))

(def mutations [create-task
                update-task
                delete-task])