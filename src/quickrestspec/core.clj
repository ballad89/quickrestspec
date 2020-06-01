(ns quickrestspec.core
  (:require [quickrestspec.parse :as parse]
            [quickrestspec.jschema :as schema]
            [quickrestspec.responses :as responses]
            [quickrestspec.generation :as generation]
            [clojure.test.check :as tc]
            [clojure.test.check.properties :as prop]
            [clojure.string :as st]
            [restpect.json :refer [GET PUT DELETE]]
            [org.bovinegenius.exploding-fish :refer [uri]]))

(defn make-request [url verb form-data])

(defn get-property-form-data [path]
  (prop/for-all [generator (:generator path)]
                (let [{:keys [responses verb]} path
                      {:keys [url body]} generator
                      verb (-> (name verb) (st/upper-case))]
                  (println "generator" generator)
                  (println "responses" responses)
                  (println "url" url)
                  (println "verb" verb)
                  (println "form" body))

                true))

(defn quick-rest [json-path]
  (let [json-uri (uri json-path)
        base-url (format "%s://%s:%d" (:scheme json-uri) (:host json-uri) (:port json-uri))
        raw-doc    (parse/get-json-from-path json-path)
        parsed-doc (parse/parse-doc raw-doc)
        paths      (generation/make-generators base-url (:paths parsed-doc))]
    (eval (conj (schema/definitions-to-specs "prefix" (:definitions parsed-doc)) 'do))
    (eval (conj (responses/paths-items-to-specs (:paths parsed-doc)) 'do))
    (mapv (fn [path]
            (tc/quick-check 5
                            (get-property-form-data path)))
          paths)))