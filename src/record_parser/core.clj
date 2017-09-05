(ns record-parser.core
  (:require [record-parser.parser :as parser]
            [clojure.string :as str]
            [record-parser.presenter :as presenter]))

(defn read-file [file-path]
  (slurp file-path))

(defn parse-records [file-path]
  (let [raw-records (read-file file-path)
        parsed-records (parser/build-user-records raw-records)]
    parsed-records))

(defn records-sorted-by-gender [file-path]
  (let [parsed-records (parse-records file-path)
        sorted-by-gender (presenter/sort-records-by-gender parsed-records)]
    (apply println "Sorted By Gender: \n"
           sorted-by-gender)))

(defn records-sorted-by-name [file-path]
  (let [parsed-records (parse-records file-path)
        sorted-by-lastname (presenter/sort-records-by-lastname parsed-records)]
    (apply println "Sorted By Last Name: \n"
           (presenter/present-records sorted-by-lastname))))

(defn -main [& args]
  (let [file-path (first args)]
    (records-sorted-by-gender file-path)
    (records-sorted-by-name file-path)))
