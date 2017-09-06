### Dependencies
- Clojure
- Java

### Setup Instructions
- Install Leiningen (Clojure Build Tool)

```
% curl https://raw.githubusercontent.com/technomancy/leiningen/stable/bin/lein > lein

% sudo mkdir -p /usr/local/bin/

% sudo mv lein /usr/local/bin/lein

% sudo chmod a+x /usr/local/bin/lein
```

- Download project dependencies

`lein deps`

### Process Records From The Commandline
`lein run` `file-name`

`file-name` should be swapped for the path to an actual file.

You can substitute different file names. For example:

`lein run user-records/records-with-space-delimiter.txt`

`lein run user-records/records-with-comma-delimiter.txt`


### The Record-Parser API

The record parser API retrieves and creates records.

To start the server, run the command:

`lein ring server`

In the browser, you can perform any of the following http requests:

[`GET Requests`]

`GET /records/gender` - Retrieves records from "user-records/records-with-space-delimiter.txt" sorted by gender

`GET /records/name` - Retrieves records sorted by lastname

`GET /records/birthdate`- Retrieves records sorted by birthdate

------

[`POST Requests`]

`POST /records?raw-record=''&file-path=''`

Creates a record, given a `raw-record` and a `file-path`.
An example of a `raw-record`:

`"Potter | Harry | Male | Blue | 01/05/1982"`

### To Run The Specs

- lein spec

### To Auto Run The Specs

- lein spec -a
