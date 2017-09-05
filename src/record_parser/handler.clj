(ns record-parser.handler
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

(defn handle-gender-records [file-path]
  (let [raw-records (slurp file-path)
        parsed-records (parser/build-user-records raw-records)
        sorted-records (presenter/sort-records-by-gender parsed-records)]
    (handle-json-response
      (presenter/present-records sorted-records))))

(defn handle-name-records [file-path]
  (let [raw-records (slurp file-path)
        parsed-records (parser/build-user-records raw-records)
        sorted-records (presenter/sort-records-by-lastname parsed-records)]
    (handle-json-response
      (presenter/present-records sorted-records))))

(defn handle-birthdate-records [file-path]
  (let [raw-records (slurp file-path)
        parsed-records (parser/build-user-records raw-records)
        sorted-records (presenter/sort-records-by-birthdate parsed-records)]
    (handle-json-response
      (presenter/present-records sorted-records))))

(defn create-record [file-path raw-record]
  (let [add-newline (spit file-path "\n" :append true)]
    (spit file-path raw-record :append true)))

(defroutes app-routes
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
  (wrap-defaults app-routes api-defaults))
