(ns record-parser.handler-spec
  (:require [speclj.core :refer :all]
            [ring.mock.request :as mock]
            [record-parser.handler :refer :all]
            [clojure.java.io :as io]))

(describe "handler"
  (context "/"
    (it "returns a 200 when response is successful"
      (let [response (app (mock/request :get "/"))]
        (should= (:status response) 200)))

    (it "returns a body message for request to root"
      (let [response (app (mock/request :get "/"))]
        (should= (:body response) "Hello! I parse and present you with sorted records"))))

  (context "/records/gender"
    (it "returns records sorted by gender"
      (let [file-path "fixtures/test-records.txt"
            response (app (mock/request :get "/records/gender"))
            record-response-string "{:last-name \"Granger\",\n :first-name \"Hermione\",\n :gender \"Female\",\n :favorite-color \"Green\",\n :birthdate \"12/05/1982\"}\n{:last-name \"Weasley\",\n :first-name \"Ginny\",\n :gender \"Female\",\n :favorite-color \"Black\",\n :birthdate \"5/05/1985\"}\n{:last-name \"Potter\",\n :first-name \"Harry\",\n :gender \"Male\",\n :favorite-color \"Blue\",\n :birthdate \"01/05/1982\"}\n{:last-name \"Weasley\",\n :first-name \"Ron\",\n :gender \"Male\",\n :favorite-color \"Green\",\n :birthdate \"10/05/1980\"}\n"]
        (should= (:body response) record-response-string)
        (should= (:status response) 200))))

  (context "/records/name"
    (it "returns records sorted by last-name"
      (let [file-path "fixtures/test-records.txt"
            response (app (mock/request :get "/records/name"))
            record-response-string "{:last-name \"Weasley\",\n :first-name \"Ginny\",\n :gender \"Female\",\n :favorite-color \"Black\",\n :birthdate \"5/05/1985\"}\n{:last-name \"Weasley\",\n :first-name \"Ron\",\n :gender \"Male\",\n :favorite-color \"Green\",\n :birthdate \"10/05/1980\"}\n{:last-name \"Potter\",\n :first-name \"Harry\",\n :gender \"Male\",\n :favorite-color \"Blue\",\n :birthdate \"01/05/1982\"}\n{:last-name \"Granger\",\n :first-name \"Hermione\",\n :gender \"Female\",\n :favorite-color \"Green\",\n :birthdate \"12/05/1982\"}\n"]
        (should= (:body response)
                 record-response-string)
        (should= (:status response) 200))))

  (context "/records/birthdate"
    (it "returns records sorted by birthdate"
      (let [file-path "fixtures/test-records.txt"
            response (app (mock/request :get "/records/birthdate"))
            record-response-string "{:last-name \"Weasley\",\n :first-name \"Ginny\",\n :gender \"Female\",\n :favorite-color \"Black\",\n :birthdate \"05/05/1985\"}\n{:last-name \"Granger\",\n :first-name \"Hermione\",\n :gender \"Female\",\n :favorite-color \"Green\",\n :birthdate \"12/05/1982\"}\n{:last-name \"Potter\",\n :first-name \"Harry\",\n :gender \"Male\",\n :favorite-color \"Blue\",\n :birthdate \"01/05/1982\"}\n{:last-name \"Weasley\",\n :first-name \"Ron\",\n :gender \"Male\",\n :favorite-color \"Green\",\n :birthdate \"10/05/1980\"}\n"]
        (should= (:body response)
                 record-response-string)
        (should= (:status response) 200))))

  (context "/records"
    (it "writes a raw-record to a file given a file-path and a raw-record"
      (let [file-path "test-file.txt"
            _ (spit file-path "")
            response (app (mock/request :post "/records" {:raw-record "Potter | Harry | Male | Blue | 01/05/1982"
                                                          :file-path file-path}))]
        (should= (slurp file-path) "\nPotter | Harry | Male | Blue | 01/05/1982")
        (should= (:status response) 404)
      (io/delete-file file-path)))

    (it "appends a raw-record to a document with existing records"
      (let [file-path "test-file.txt"
            _ (spit file-path "Potter | Harry | Male | Blue | 01/05/1982")
            response (app (mock/request :post "/records" {:raw-record "Granger | Hermione | Female | Green | 12/05/1982"
                                                          :file-path file-path}))]
        (should= (slurp file-path) "Potter | Harry | Male | Blue | 01/05/1982\nGranger | Hermione | Female | Green | 12/05/1982")
        (should= (:status response) 404)
      (io/delete-file file-path))))

  )
