(ns record-parser.presenter-spec
  (:require [speclj.core :refer :all]
            [record-parser.presenter :as p]))

(describe "presenter"
  (it "presents parsed records in string format"
    (let [parsed-records [{:last-name "Potter"
                           :first-name "Harry"
                           :gender "Male"
                           :favorite-color "Blue"
                           :birthdate "01/05/1982"}]]
      (should= (p/present-records parsed-records)
               "{:last-name \"Potter\",\n :first-name \"Harry\",\n :gender \"Male\",\n :favorite-color \"Blue\",\n :birthdate \"01/05/1982\"}\n")))

  (it "presents parsed records in string format"
    (let [parsed-records [{:last-name "Granger"
                           :first-name "Hermione"
                           :gender "Female"
                           :favorite-color "Green"
                           :birthdate "01/09/1982"}]]
      (should= (p/present-records parsed-records)
               "{:last-name \"Granger\",\n :first-name \"Hermione\",\n :gender \"Female\",\n :favorite-color \"Green\",\n :birthdate \"01/09/1982\"}\n")))

  (it "adds a newline after every record"
    (let [parsed-records [{:last-name "Granger"}
                          {:last-name "Potter"}]]
      (should= (p/present-records parsed-records)
               "{:last-name \"Granger\"}\n{:last-name \"Potter\"}\n")))

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

  (it "sorts records by birthdate ascending"
    (let [mapped-record [{:birthdate "12/1/1990"}
                         {:birthdate "06/05/2005"}
                         {:birthdate "11/05/1989"}]]

      (should= [{:birthdate "06/05/2005"}
                {:birthdate "12/01/1990"}
                {:birthdate "11/05/1989"}]
               (p/sort-records-by-birthdate mapped-record))))

  (it "sorts records by birthdate ascending"
    (let [mapped-record [{:birthdate "12/01/1980"}
                         {:birthdate "06/05/2000"}
                         {:birthdate "11/05/2020"}]]

      (should= [{:birthdate "11/05/2020"}
                {:birthdate "06/05/2000"}
                {:birthdate "12/01/1980"}]
               (p/sort-records-by-birthdate mapped-record))))
  )
