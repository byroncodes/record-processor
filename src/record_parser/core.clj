(ns record-parser.core
  (:require [clojure.string :as str]))

(defn- split-on-newline [raw-records]
  (str/split raw-records #"\n"))

(defn- remove-delimiter [raw-record]
  (str/replace raw-record #"\| " ""))

(defn vectorize-records [record-string-coll]
  (partition 1 record-string-coll))

(defn map-record [record-vector]
  (let [record-keys [:last-name :first-name :gender :favorite-color :birthdate]]
    (vector (zipmap record-keys
              (apply #(str/split % #" ") record-vector)))))

(defn map-all-records [record-vectors]
  (map map-record record-vectors))

(defn remove-all-delimiters [raw-records]
  (map remove-delimiter raw-records))

(defn map-raw-records [raw-records]
  (let [split-on-newline (split-on-newline raw-records)
        remove-all-delimiters (remove-all-delimiters split-on-newline)
        vectorize-records (vectorize-records remove-all-delimiters)]
    (map-all-records vectorize-records)))

(defn read-file [file-path]
  (slurp file-path))

(defn -main [& args]
  (let [file-contents (read-file args)]
    (println file-contents)))
