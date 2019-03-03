#!/usr/bin/env bash

#=============================================================
# Usage
# 
# 1) Set the DATASOURCE_IP variable to the URL of the MySQL DB.
# 2) Set the DATASOURCE_NAME variable to the name of the database.
# 3) Run:  sh run-service.sh
#
#=============================================================

docker run -d -p 80:8080 \
-e SPRING_DATASOURCE_URL=jdbc:mysql://$DATASOURCE_IP/$DATASOURCE_NAME \
--name atm_svc atm_svc
