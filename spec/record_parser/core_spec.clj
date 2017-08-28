(ns record-parser.core-spec
  (:require [speclj.core :refer :all]
            [record-parser.core :as c]
            [clojure.java.io :as io]))

(describe "core"
  (it "reads contents from file, given a file path"
    (let [file-path "test-file.txt"
          _ (spit file-path "test file contents")]
      (should= (c/read-file file-path)
               "test file contents")
      (io/delete-file file-path)))

  (it "reads contents from test-file-2, given a file path"
    (let [file-path "test-file-2.txt"
          _ (spit file-path "second test file contents")]
      (should= (c/read-file file-path)
               "second test file contents")
      (io/delete-file file-path)))
)
