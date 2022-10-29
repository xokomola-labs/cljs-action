(ns action
  (:require ["@actions/core" :as core]
            ["@actions/github" :as github]
            ["saxon-js" :as xslt]))

(defn node-slurp
  "Takes a file path string and reads it's contents as UTF-8 text."
  [path]
  (let [fs (js/require "fs")]
    (.readFileSync fs path "utf8")))

(defn node-write
  "Takes a path string and a contents string and writes it to the 
   filesystem."
  [path contents]
  (let [fs (js/require "fs")]
    (.writeFileSync fs path contents)))

(def xf
  (.. xslt -default -transform))

(defn transform
  [source]
  (let [options {:stylesheetFileName "source.sef.json"
                 :sourceText source
                 :sourceType "xml"
                 :destination "serialized"}]
    (-> (xf (clj->js options))
        (. -principalResult))))

(try
  (let [name-to-greet (.getInput core "who-to-greet")
        _ (.log js/console (str "Hello " name-to-greet "!"))
        time (.toTimeString (js/Date.))
        _ (.setOutput core "time" time)
        payload (.. github -context -payload)
        payload (js/JSON.stringify payload nil 2)
        _ (.log js/console (str "The event payload: " payload))]
      (.log js/console (str (transform (node-slurp "source.xml")))))
  (catch :default e
    (.setFailed core (.-message e))))
