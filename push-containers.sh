#!/bin/bash
container_list="fiohog-service cpuhog-service datahog-service memhog-service gateway-service"
for c in $container_list
do
   cname=eoinwoods/$c
   echo "Pushing container $cname"
   docker push $cname
done
