(ns app.backend.resolvers.core
  (:require
    [app.backend.resolvers.user :as user]
    [app.backend.resolvers.task :as task]))

(def resolvers [user/resolvers
                task/resolvers])