# quickrestspec

A Clojure OpenAPI integration test library designed to automatically generate and validate integration tests.

### Still in development

The idea is that from an OpenAPI specification, you know what the different inputs and corresponding outputs should be.
This library aims to parse the spec document, and generate tests using and input specs and validate the output corresponds to the output specs

This library is based heavily on the paper [QuickREST: Property-based Test Generation of OpenAPI-Described RESTful APIs](https://arxiv.org/pdf/1912.09686.pdf) by Stefan Karlsson, Adnan Causevic and Daniel Sundmark and their code which they were kind enough to share with me

## Usage

```clojure
(ns example.core-test
  (:require [clojure.test :refer :all]
            [quickrestspec.core :as qc]
            [payment-gateway.application :as a]))


(deftest fetch-json
  (qc/quick-rest  "http://localhost:3000/api/swagger.json"))
```

## License

Copyright © 2020 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
