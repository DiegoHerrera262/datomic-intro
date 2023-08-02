(ns backend.mutations.core
  (:require
    [backend.mutations.user :as user]
    [backend.mutations.task :as task]))

(def mutations [user/mutations
                task/mutations])
