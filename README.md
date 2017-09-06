### Dependencies
- Clojure
- Java

### Setup Instructions
- Install Leiningen (Clojure Build Tool)

% curl https://raw.githubusercontent.com/technomancy/leiningen/stable/bin/lein > lein
% sudo mkdir -p /usr/local/bin/
% sudo mv lein /usr/local/bin/lein
% sudo chmod a+x /usr/local/bin/lein

- Install project dependencie
`lein deps`

### Process Records From The Commandline

Run the following command:

`lein run user-records/records-with-space-delimiter.txt`

You can substitute different files with different record types as an argument.

For example:

`lein run user-records/records-with-comma-delimiter.txt`

Will process records in the `records-with-comma-delimiter` file

### The Record-Parser API

The record parser retrieves and records.

To start the server, run the command:

`lein ring server`

In the browser, perform any of the following http requests:

`GET /records/gender` - Retrieves records from "user-records/records-with-space-delimiter.txt" sorted by gender
`GET /records/name` - Retrieves records sorted by lastname
`GET /records/birthdate`- Retrieves records sorted by birthdate

`POST /records?raw-record=''&file-path=''`

Creates a record, given a raw-record string and a file path.
Example of a raw-record string: "Potter | Harry | Male | Blue | 01/05/1982"

### To Run The Specs

- lein spec

### To Auto Run The Specs

- lein spec -a
