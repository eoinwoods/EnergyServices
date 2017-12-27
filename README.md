# Energy Microservices

This repository contains a small number of generic, stub microservices
that are intended for investigating energy consumption at an application level.

The services are:

* Burner  - a microservice that does nothing but "burn" 1s of cpu time
* Memhog  - a microservice that uses memory when invoked
* Datahog - a microservice that writes data to its own MongoDB instance

Build the applications by running "./gradlew build" in each subdirectory.

These are Spring Boot applications, so just run them as 
"java -jar build/libs/jarname.jar" in each subdirectory.


