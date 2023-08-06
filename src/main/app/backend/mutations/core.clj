(ns app.backend.mutations.core
  (:require
    [app.backend.mutations.user :as user]
    [app.backend.mutations.task :as task]))

(def mutations [user/mutations
                task/mutations])
