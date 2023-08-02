(ns backend.resolvers.core
  (:require
    [backend.resolvers.user :as user]
    [backend.resolvers.task :as task]))

(def resolvers [user/resolvers
                task/resolvers])