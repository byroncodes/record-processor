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
               "{:last-name \"Potter\",\n :first-name \"Harry\",\n :gender \"Male\",\n :favorite-color \"Blue\",\n :birthdate \"01/05/1982\"}")))

  (it "presents parsed records in string format"
    (let [parsed-records [{:last-name "Granger"
                           :first-name "Hermione"
                           :gender "Female"
                           :favorite-color "Green"
                           :birthdate "01/09/1982"}]]
      (should= (p/present-records parsed-records)
               "{:last-name \"Granger\",\n :first-name \"Hermione\",\n :gender \"Female\",\n :favorite-color \"Green\",\n :birthdate \"01/09/1982\"}")))
  )
