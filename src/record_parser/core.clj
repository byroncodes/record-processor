(ns record-parser.core
  (:require [record-parser.parser :as parser]
            [clojure.string :as str]
            [record-parser.presenter :as presenter]))

(defn present-records-sorted-by-gender [parsed-records]
  (let [sorted-records (presenter/sort-records-by-gender parsed-records)]
    (presenter/present-records sorted-records)))

(defn present-records-sorted-by-lastname [parsed-records]
  (let [sorted-records (presenter/sort-records-by-lastname parsed-records)]
    (presenter/present-records sorted-records)))

(defn present-records-sorted-by-birthdate [parsed-records]
  (let [sorted-records (presenter/sort-records-by-birthdate parsed-records)]
    (presenter/present-records sorted-records)))

(defn print-records [file-path]
  (let [raw-records (slurp file-path)
        parsed-records (parser/build-user-records raw-records)]
    (println (present-records-sorted-by-gender parsed-records))
    (println (present-records-sorted-by-lastname parsed-records))
    (println (present-records-sorted-by-birthdate parsed-records))))

(defn -main [& args]
  (let [file-path (first args)]
    (print-records file-path)))
