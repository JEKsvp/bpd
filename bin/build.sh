#!/bin/bash

display_usage() {
  echo -e "\n This script builds docker image"
  echo -e "\nUSAGE:\n\n ${0} <build-tag>\n"
}

if [ $# -lt 1 ]; then
  display_usage
  exit 1
fi

mvn clean package

WORK_DIR=$(cd "$(dirname "$0")"; cd ..; pwd)

echo -e " \n WORK_DIR $WORK_DIR "

cd "$WORK_DIR" || exit

docker build . -t "$1"