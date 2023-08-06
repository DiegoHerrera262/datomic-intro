(ns app.backend.database.queries.shared
  (:require
    [datomic.client.api :as d]))

(defn tx-retract
  [conn id]
  (d/transact conn {:tx-data [[:db/retractEntity id]]}))

(defn tx-add
  ([conn data]
   (let [res (d/transact conn {:tx-data [data]})
         db-id (-> res
                   :tempids
                   vals
                   first)]
     (assoc data :db/id db-id))
   )
  ([conn id data]
   (let [tx-data (assoc data :db/id id)]
     (d/transact conn {:tx-data [tx-data]})
     tx-data)))

(defn remap-id
  [entity key]
  (assoc entity key (:db/id entity)))