(ns record-parser.presenter
  (:require [clojure.string :as str]
            [clj-time.format :as f]))

(defn stringify-record [parsed-record]
  (str/replace (str parsed-record "\n") #"," ",\n"))

(defn present-records [parsed-records]
  (->> parsed-records
      (map stringify-record)
      (apply str)))

(defn sort-records-by-gender [mapped-records]
  (->> mapped-records
       (sort-by :last-name)
       (sort-by :gender)))

(defn sort-records-by-lastname [mapped-record]
  (->> mapped-record
       (sort-by :last-name)
       (reverse)))

(defn- parse-date [date]
  (let [date-formatter (f/formatter "MM/dd/yyyy")]
    (f/parse date-formatter date)))

(defn- unparse-date [date]
  (let [date-formatter (f/formatter "MM/dd/yyyy")]
    (f/unparse date-formatter date)))

(defn- parse-birthdate-values [records]
  (map (fn [record] (update record :birthdate parse-date))
       records))

(defn- sort-birthdate-values-acsending [records]
  (reverse (sort-by :birthdate records)))

(defn- unparse-birthdate-values [records]
  (map (fn [record] (update record :birthdate unparse-date))
       records))

(defn sort-records-by-birthdate [records]
  (-> records
      (parse-birthdate-values)
      (sort-birthdate-values-acsending)
      (unparse-birthdate-values)))
