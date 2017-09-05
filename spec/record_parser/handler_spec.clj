(ns record-parser.handler-spec
  (:require [speclj.core :refer :all]
            [ring.mock.request :as mock]
            [record-parser.handler :refer :all]))

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
            response (app (mock/request :get "/records/gender" {:file-path file-path}))
            record-response-string "{:last-name \"Granger\",\n :first-name \"Hermione\",\n :gender \"Female\",\n :favorite-color \"Green\",\n :birthdate \"12/05/1982\"}\n{:last-name \"Weasley\",\n :first-name \"Ginny\",\n :gender \"Female\",\n :favorite-color \"Black\",\n :birthdate \"5/05/1985\"}\n{:last-name \"Potter\",\n :first-name \"Harry\",\n :gender \"Male\",\n :favorite-color \"Blue\",\n :birthdate \"01/05/1982\"}\n{:last-name \"Weasley\",\n :first-name \"Ron\",\n :gender \"Male\",\n :favorite-color \"Green\",\n :birthdate \"10/05/1980\"}\n"]
        (should= (:body response) record-response-string)
        (should= (:status response) 200))))

  (context "/records/name"
    (it "returns records sorted by last-name"
      (let [file-path "fixtures/test-records.txt"
            response (app (mock/request :get "/records/name" {:file-path file-path}))
            record-response-string "{:last-name \"Weasley\",\n :first-name \"Ginny\",\n :gender \"Female\",\n :favorite-color \"Black\",\n :birthdate \"5/05/1985\"}\n{:last-name \"Weasley\",\n :first-name \"Ron\",\n :gender \"Male\",\n :favorite-color \"Green\",\n :birthdate \"10/05/1980\"}\n{:last-name \"Potter\",\n :first-name \"Harry\",\n :gender \"Male\",\n :favorite-color \"Blue\",\n :birthdate \"01/05/1982\"}\n{:last-name \"Granger\",\n :first-name \"Hermione\",\n :gender \"Female\",\n :favorite-color \"Green\",\n :birthdate \"12/05/1982\"}\n"]
        (should= (:body response)
                 record-response-string)
        (should= (:status response) 200))))

  (context "/records/birthdate"
    (it "returns records sorted by birthdate"
      (let [file-path "fixtures/test-records.txt"
            response (app (mock/request :get "/records/birthdate" {:file-path file-path}))
            record-response-string "{:last-name \"Weasley\",\n :first-name \"Ginny\",\n :gender \"Female\",\n :favorite-color \"Black\",\n :birthdate \"05/05/1985\"}\n{:last-name \"Granger\",\n :first-name \"Hermione\",\n :gender \"Female\",\n :favorite-color \"Green\",\n :birthdate \"12/05/1982\"}\n{:last-name \"Potter\",\n :first-name \"Harry\",\n :gender \"Male\",\n :favorite-color \"Blue\",\n :birthdate \"01/05/1982\"}\n{:last-name \"Weasley\",\n :first-name \"Ron\",\n :gender \"Male\",\n :favorite-color \"Green\",\n :birthdate \"10/05/1980\"}\n"]
        (should= (:body response)
                 record-response-string)
        (should= (:status response) 200))))
  )
