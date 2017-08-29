(ns record-parser.parser-spec
  (:require [speclj.core :refer :all]
            [record-parser.parser :as p]
            [clojure.java.io :as io]))

(describe "parser"
  (it "removes pipe characters from a collection of raw records"
    (let [records ["Potter | Harry | Male | Blue | 4/5/1992"
                   "Potter | Harry | Male | Blue | 4/5/1992"]]
      (should= ["Potter Harry Male Blue 4/5/1992"
                "Potter Harry Male Blue 4/5/1992"]
               (p/remove-all-delimiters records))))

  (it "removes pipe characters from a collection of raw records"
    (let [records  ["Potter | Harry | Male | Blue | 4/5/1992"
                    "Granger | Hermione | Female | Green | 7/5/1992"]]
      (should= ["Potter Harry Male Blue 4/5/1992"
                "Granger Hermione Female Green 7/5/1992"]
               (p/remove-all-delimiters records))))

  (it "splits record strings into vectors"
    (let [record-string-coll  ["Potter Harry Male Blue 4/5/1992"
                               "Granger Hermione Female Green 7/5/1992"]]
      (should= [["Potter Harry Male Blue 4/5/1992"]
                ["Granger Hermione Female Green 7/5/1992"]]
               (p/vectorize-records record-string-coll))))

  (it "splits 4 record strings into vectors"
    (let [record-string-coll ["Granger Hermione Female Green 7/5/1992"
                              "Granger Hermione Female Green 7/5/1992"
                              "Granger Hermione Female Green 7/5/1992"
                              "Granger Hermione Female Green 7/5/1992"]]
      (should= [["Granger Hermione Female Green 7/5/1992"]
                ["Granger Hermione Female Green 7/5/1992"]
                ["Granger Hermione Female Green 7/5/1992"]
                ["Granger Hermione Female Green 7/5/1992"]]
               (p/vectorize-records record-string-coll))))

  (it "maps a raw record's values to its corresponding keys"
    (let [record-vec ["Potter Harry Male Blue 4/5/1992"]]
      (should= [{:last-name "Potter"
                 :first-name "Harry"
                 :gender "Male"
                 :favorite-color "Blue"
                 :birthdate "4/5/1992"}]
               (p/map-record record-vec))))

  (it "maps a raw record's values to its corresponding keys"
    (let [record-vec ["Granger Hermione Female Green 7/5/1992"]]
      (should= [{:last-name "Granger"
                 :first-name "Hermione"
                 :gender "Female"
                 :favorite-color "Green"
                 :birthdate "7/5/1992"}]
               (p/map-record record-vec))))

  (it "maps 1 vectorized record to its corresponding keys"
    (let [vectorized-records [["Granger Hermione Female Green 7/5/1992"]]]
      (should= [{:last-name "Granger"
                 :first-name "Hermione"
                 :gender "Female"
                 :favorite-color "Green"
                 :birthdate "7/5/1992"}]
               (p/map-all-records vectorized-records))))

  (it "maps 2 vectorized records to their corresponding keys"
    (let [vectorized-records [["Granger Hermione Female Green 7/5/1992"]
                              ["Potter Harry Male Blue 4/5/1992"]]]
      (should= [{:last-name "Granger"
                 :first-name "Hermione"
                 :gender "Female"
                 :favorite-color "Green"
                 :birthdate "7/5/1992"}
                {:last-name "Potter"
                 :first-name "Harry"
                 :gender "Male"
                 :favorite-color "Blue"
                 :birthdate "4/5/1992"}]
               (p/map-all-records vectorized-records))))

  (it "builds 1 record map given 1 raw record"
    (let [raw-records-string "Granger | Hermione | Female | Green | 7/5/1992\n"]
      (should= [{:last-name "Granger"
                 :first-name "Hermione"
                 :gender "Female"
                 :favorite-color "Green"
                 :birthdate "7/5/1992"}]
               (p/map-raw-records raw-records-string))))

  (it "builds 2 record maps given 2 raw records"
    (let [raw-records-string "Granger | Hermione | Female | Green | 7/5/1992\nPotter | Harry | Male | Blue | 4/5/1992"]
      (should= [{:last-name "Granger"
                 :first-name "Hermione"
                 :gender "Female"
                 :favorite-color "Green"
                 :birthdate "7/5/1992"}
                {:last-name "Potter"
                 :first-name "Harry"
                 :gender "Male"
                 :favorite-color "Blue"
                 :birthdate "4/5/1992"}]
               (p/map-raw-records raw-records-string))))

  (it "sorts records by gender with 'Females' appearing first"
    (let [mapped-record [{:gender "Female"}
                         {:gender "Male"}
                         {:gender "Female"}]]
      (should= "Female"
               (:gender (first (p/sort-records-by-gender mapped-record))))

      (should= "Female"
               (:gender (second (p/sort-records-by-gender mapped-record))))

      (should= "Male"
               (:gender (nth (p/sort-records-by-gender mapped-record) 2)))))

  (it "sorts records by last name ascending"
    (let [mapped-record [{:last-name "Granger"
                          :gender "Female"}
                         {:last-name "Potter"
                          :gender "Male"}
                         {:last-name "Weasley"
                          :gender "Female"}
                         {:last-name "Lestrange"
                          :gender "Female"}
                         {:last-name "Black"
                          :gender "Male"}]]

      (should= [{:last-name "Granger"
                 :gender "Female"}
                {:last-name "Lestrange"
                 :gender "Female"}
                {:last-name "Weasley"
                 :gender "Female"}
                {:last-name "Black"
                 :gender "Male"}
                {:last-name "Potter"
                 :gender "Male"}]
               (p/sort-records-by-gender mapped-record))))

  (it "sorts records by last name ascending"
    (let [mapped-record [{:last-name "Granger"}
                         {:last-name "Potter"}
                         {:last-name "Weasley"}]]

      (should= [{:last-name "Weasley"}
                {:last-name "Potter"}
                {:last-name "Granger"}]
               (p/sort-records-by-lastname mapped-record))))

  (it "sorts records by last name ascending"
    (let [mapped-record [{:last-name "A"}
                         {:last-name "B"}
                         {:last-name "C"}]]

      (should= [{:last-name "C"}
                {:last-name "B"}
                {:last-name "A"}]
               (p/sort-records-by-lastname mapped-record))))

  )
