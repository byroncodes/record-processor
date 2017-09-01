(ns record-parser.parser
  (:require [clojure.string :as str]
            [clj-time.core :as clj-time]
            [clj-time.coerce :as c]
            [clj-time.format :as f]))

(defn- split-on-newline [raw-records]
  (str/split raw-records #"\n"))

(defn- remove-delimiters [raw-record]
  (-> raw-record
      (str/replace #"\| " "")
      (str/replace #"," "")))

(defn normalize-records [raw-records]
  (-> raw-records
      (remove-delimiters)
      (split-on-newline)))

(defn build-user-record [record-vector]
  (zipmap [:last-name :first-name :gender :favorite-color :birthdate]
          record-vector))

(defn vectorize-records [raw-records]
  (let [normalized-records (normalize-records raw-records)]
    (map #(str/split % #" ") normalized-records)))

(defn build-user-records [raw-records]
  (let [vectorized-records (vectorize-records raw-records)]
    (map (fn [record] (build-user-record record))
         vectorized-records)))

(defn sort-records-by-gender [mapped-records]
  (let [sorted-by-last-name (sort-by :last-name mapped-records)]
    (sort-by :gender sorted-by-last-name)))

(defn sort-records-by-lastname [mapped-record]
  (reverse (sort-by :last-name mapped-record)))
