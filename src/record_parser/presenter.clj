(ns record-parser.presenter
  (:require [clojure.string :as str]))

(defn stringify-record [parsed-record]
  (str/replace (str parsed-record) #"," ",\n"))

(defn present-records [parsed-records]
  (->> parsed-records
      (map stringify-record)
      (apply str)))
