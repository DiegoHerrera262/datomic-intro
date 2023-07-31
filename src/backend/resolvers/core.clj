(ns backend.resolvers.core
  (:require
    [backend.resolvers.user :as user-resolvers]))

(def resolvers [user-resolvers/resolvers])