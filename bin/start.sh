#!/bin/bash

display_usage() {
  echo -e "\n This script starts container"
  echo -e "\nUSAGE:\n\n ${0} <build-tag>\n"
}

if [ $# -lt 1 ]; then
  display_usage
  exit 1
fi

docker run \
-p 8081:8081 \
-e JAVA_OPTS="-Dspring.profiles.active=dev" \
-e SPRING_DATA_MONGODB_HOST="docker.for.mac.host.internal" \
"$1"