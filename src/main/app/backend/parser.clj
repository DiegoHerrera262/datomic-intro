(ns app.backend.parser
  (:require
    [app.backend.mutations.core :refer [mutations]]
    [app.backend.resolvers.core :refer [resolvers]]
    [com.wsscode.pathom.core :as p]
    [com.wsscode.pathom.connect :as pc]
    [taoensso.timbre :as log]))

(def registry [resolvers mutations])

(def parser
  (p/parser {::p/env     {::p/reader                 [p/map-reader
                                                      pc/reader2
                                                      pc/ident-reader
                                                      pc/index-reader
                                                      p/env-placeholder-reader]
                          ::pc/mutation-join-globals [:tempids]}
             ::p/mutate  pc/mutate
             ::p/plugins [(pc/connect-plugin {::pc/register registry})
                          p/error-handler-plugin
                          p/trace-plugin]}))

(defn api-parser [query]
  (log/info "Process" query)
  (parser {} query))