(ns record-parser.core)

(defn read-file [file-path]
  (slurp file-path))


(defn -main [& args]
  (let [file-contents (read-file args)]
    (println file-contents)))
