(ns record-parser.parser-spec
  (:require [speclj.core :refer :all]
            [record-parser.parser :as p]
            [clojure.java.io :as io]))

(describe "parser"
  (it "returns vector with records stripped of pipe delimiters"
    (let [raw-records "Granger | Hermione | Female | Green | 7/5/1992"]
      (should= (p/normalize-records raw-records)
               ["Granger Hermione Female Green 7/5/1992"])))

  (it "returns vector with records stripped of comma delimiters"
    (let [raw-records "Granger, Hermione, Female, Green, 7/5/1992\nPotter, Harry, Male, Blue, 4/5/1992"]
      (should= (p/normalize-records raw-records)
               ["Granger Hermione Female Green 7/5/1992"
                "Potter Harry Male Blue 4/5/1992"])))

  (it "returns vector with records if raw-record delimiter's are spaces"
    (let [raw-records "Granger Hermione Female Green 7/5/1992\nPotter Harry Male Blue 4/5/1992"]
      (should= (p/normalize-records raw-records)
               ["Granger Hermione Female Green 7/5/1992"
                "Potter Harry Male Blue 4/5/1992"])))

  (it "returns vector with records stripped of newline characters"
    (let [raw-records "Granger, Hermione, Female, Green, 7/5/1992\nPotter, Harry, Male, Blue, 4/5/1992"]
      (should= (p/normalize-records raw-records)
               ["Granger Hermione Female Green 7/5/1992"
                "Potter Harry Male Blue 4/5/1992"])))

  (it "creates a vector with normalized text for 2 raw-records"
    (let [record-string-coll "Granger | Hermione | Female | Green | 7/5/1992\nPotter | Harry | Male | Blue | 4/5/1992"]
      (should== [["Potter" "Harry" "Male" "Blue" "4/5/1992"]
                 ["Granger" "Hermione" "Female" "Green" "7/5/1992"]]
                (p/vectorize-records record-string-coll))))

  (it "creates a vector with normalized text for 1 raw-record"
    (let [record-string-coll "Granger | Hermione | Female | Green | 7/5/1992"]
      (should= [["Granger" "Hermione" "Female" "Green" "7/5/1992"]]
               (p/vectorize-records record-string-coll))))

  (it "builds 1 record map given 1 raw record"
    (let [raw-records-string "Granger | Hermione | Female | Green | 7/5/1992\n"]
      (should= [{:last-name "Granger"
                 :first-name "Hermione"
                 :gender "Female"
                 :favorite-color "Green"
                 :birthdate "7/5/1992"}]
               (p/build-user-records raw-records-string))))

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
               (p/build-user-records raw-records-string))))

  )
