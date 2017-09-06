(ns record-parser.core-spec
  (:require [speclj.core :refer :all]
            [record-parser.core :as c]
            [clojure.java.io :as io]))

(describe "core"
  (it "presents records sorted by gender"
    (let [parsed-records [{:gender "Female"}
                          {:gender "Male"}
                          {:gender "Female"}]]
      (should= "{:gender \"Female\"}\n{:gender \"Female\"}\n{:gender \"Male\"}\n"
               (c/present-records-sorted-by-gender parsed-records))))

  (it "presents records sorted by lastname & gender - ascending"
    (let [parsed-records [{:last-name "Granger"
                          :gender "Female"}
                         {:last-name "Potter"
                          :gender "Male"}
                         {:last-name "Lestrange"
                          :gender "Female"}
                         {:last-name "Black"
                          :gender "Male"}]]
      (should= "{:last-name \"Potter\",\n :gender \"Male\"}\n{:last-name \"Lestrange\",\n :gender \"Female\"}\n{:last-name \"Granger\",\n :gender \"Female\"}\n{:last-name \"Black\",\n :gender \"Male\"}\n"
               (c/present-records-sorted-by-lastname parsed-records))))

  (it "presents records sorted by birthdate - ascending"
    (let [parsed-records [{:birthdate "12/1/1990"}
                          {:birthdate "06/05/2005"}
                          {:birthdate "11/05/1989"}]]
      (should= "{:birthdate \"06/05/2005\"}\n{:birthdate \"12/01/1990\"}\n{:birthdate \"11/05/1989\"}\n"
               (c/present-records-sorted-by-birthdate parsed-records))))

)
