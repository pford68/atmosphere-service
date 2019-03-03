#!/usr/bin/env bash

# Run me before starting the container in order to delete the old version

docker ps -a | awk '{print $1}' | xargs docker rm