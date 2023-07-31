(ns backend.mutations.core
  (:require
    [backend.mutations.user :as user-mutations]))

(def mutations [user-mutations/mutations])
