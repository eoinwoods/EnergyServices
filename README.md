# Energy Microservices

This repository contains a small number of generic, stub microservices
that are intended for investigating energy consumption at an application level.

The services are:

* Cpuhog  - a microservice that does nothing but "burn" 1s of cpu time
* Memhog  - a microservice that uses memory when invoked
* Datahog - a microservice that writes data to its own MongoDB instance
* Fiohog  - a microservice that writes data to the local file system
* Gateway - a microservice that runs scenarios using the other services

Build the applications by running "../gradlew build" in each subdirectory
or "./gradlew build" in the root directory to build everything.

To ship the containers for all services run "./gradlew build docker" in
this directory and then run push-containers.sh to push to Docker Hub.

These are Spring Boot applications, so just run them as 
"java -jar build/libs/jarname.jar" in each subdirectory.


