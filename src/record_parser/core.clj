(ns record-parser.core
  (:require [record-parser.parser :as parser]
            [clojure.string :as str]))

(defn read-file [file-path]
  (slurp file-path))

(defn stringify-record [parsed-record]
  (str (str/replace (str parsed-record) #"," ",\n") "\n\n"))

(defn parse-records [file-path]
  (let [raw-records (read-file file-path)
        parsed-records (parser/map-raw-records raw-records)]
    parsed-records))

(defn present-records [parsed-records]
  (map stringify-record parsed-records))

(defn -main [& args]
  (let [file-path (first args)
        parsed-records (parse-records file-path)
        sorted-by-gender (parser/sort-records-by-gender parsed-records)
        sorted-by-lastname (parser/sort-records-by-lastname parsed-records)]
    (apply println "Sorted By Gender: \n" (present-records sorted-by-gender))
    (apply println "Sorted By Last Name: \n" (present-records sorted-by-lastname))
    ))
