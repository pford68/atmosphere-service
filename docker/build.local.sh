#! /bin/bash -e

rm -rf build
mkdir build
cp ../build/libs/atmosphere-service.jar build

docker build -t atm_svc -f Dockerfile.local .
