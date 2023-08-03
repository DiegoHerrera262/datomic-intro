(ns backend.examples
  (:require
    [backend.parser :refer [api-parser]]))

(comment
  ;; Create operations
  (api-parser '[(create-user {:user/name  "alejozen98"
                              :user/email "alejo@gmail.com"
                              :user/role  :owner})])
  (api-parser '[(create-user {:user/name  "alejozen_crack98"
                              :user/email "alejo_crack@gmail.com"
                              :user/role  :reporter})])
  (api-parser '[(create-task {:task/assignee    [:user/id "e07c7577-1ace-4aa5-8b34-0c3a1994ee07"]
                              :task/description "Simple backend with datomic"})])
  (api-parser '[(create-task {:task/assignee    [:user/id "64ea9810-6afb-4cef-83b0-a4dba0444d3c"]
                              :task/description "Model to-many relation with datomic"})])

  ;; Read operations
  (api-parser '[{[:user/id "e07c7577-1ace-4aa5-8b34-0c3a1994ee07"] [:user/id
                                                                    :user/name
                                                                    :user/role
                                                                    :user/email]}])
  (api-parser '[{[:user/id "64ea9810-6afb-4cef-83b0-a4dba0444d3c"] [:user/id
                                                                    :user/name
                                                                    :user/role
                                                                    :user/email]}])
  (api-parser '[{[:task/id "9a344dfd-60ef-4a2e-b4a3-17c7387d2e5f"] [:task/id
                                                                    :task/description
                                                                    :task/status
                                                                    {:task/assignee
                                                                     [:user/id
                                                                      :user/name
                                                                      :user/role
                                                                      :user/email]}]}])
  (api-parser '[{[:task/id "650e0863-059e-4567-a3ba-14191aed9391"] [:task/id
                                                                    :task/description
                                                                    :task/status
                                                                    {:task/assignee
                                                                     [:user/id
                                                                      :user/name
                                                                      :user/role
                                                                      :user/email]}]}])

  ;; Update operations
  (api-parser '[(update-user {:user/id   "e07c7577-1ace-4aa5-8b34-0c3a1994ee07"
                              :user/role :reporter})])
  (api-parser '[(update-user {:user/id   "64ea9810-6afb-4cef-83b0-a4dba0444d3c"
                              :user/name "alejozen_feli98"})])
  (api-parser '[(update-task {:task/id          "650e0863-059e-4567-a3ba-14191aed9391"
                              :task/description "New task in queue"
                              :task/assignee    [:user/id "64ea9810-6afb-4cef-83b0-a4dba0444d3c"]})])
  )