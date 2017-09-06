(ns record-parser.record-controller
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [record-parser.parser :as parser]
            [record-parser.presenter :as presenter]
            [record-parser.core :as core]))

(def records-filepath "user-records/records-with-space-delimiter.txt")

(defn- handle-json-response [body]
  {:status 200
   :headers {"Content-Type" "application/javascript"}
   :body body})

(defn parsed-records [file-path]
  (-> file-path
      (slurp)
      (parser/build-user-records)))

(defn handle-gender-records [file-path]
  (let [parsed-records (parsed-records file-path)]
    (handle-json-response
      (core/present-records-sorted-by-gender parsed-records))))

(defn handle-name-records [file-path]
  (let [parsed-records (parsed-records file-path)]
    (handle-json-response
      (core/present-records-sorted-by-lastname parsed-records))))

(defn handle-birthdate-records [file-path]
  (let [parsed-records (parsed-records file-path)]
    (handle-json-response
      (core/present-records-sorted-by-birthdate parsed-records))))

(defn create-record [file-path raw-record]
  (let [add-newline (spit file-path "\n" :append true)]
    (spit file-path raw-record :append true))
  {:status 201
   :headers {"Content-Type" "application/javascript"}
   :body "Record creation successful!"})

(defroutes record-handler
  (GET "/records/gender"
       request
       (handle-gender-records records-filepath))

  (GET "/records/birthdate"
       request
       (handle-birthdate-records records-filepath))

  (GET "/records/name"
       request
       (handle-name-records records-filepath))

  (POST "/records"
        {params :params}
        (create-record (:file-path params)
                       (:raw-record params)))

  (GET "/" [] {:status 200
               :headers {"Content-Type" "application/javascript"}
               :body  "Hello! I parse and present you with sorted records"})
  (route/not-found "Not Found"))

(def app
  (wrap-defaults record-handler api-defaults))
