#!/bin/bash

IMAGE_NAME=jeksvp/bpd:latest
mvn clean package

WORK_DIR=$(cd "$(dirname "$0")"; cd ..; pwd)

echo -e " \n WORK_DIR $WORK_DIR "

cd "$WORK_DIR" || exit

docker build . -t "$IMAGE_NAME"