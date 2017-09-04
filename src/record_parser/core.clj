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

(defn -main [& args]
  (let [file-path (first args)
        parsed-records (parse-records file-path)
        sorted-by-gender (parser/sort-records-by-gender parsed-records)
        sorted-by-lastname (parser/sort-records-by-lastname parsed-records)]
    (apply println "Sorted By Gender: \n" (presenter/present-records sorted-by-gender))
    (apply println "Sorted By Last Name: \n" (presenter/present-records sorted-by-lastname))))
