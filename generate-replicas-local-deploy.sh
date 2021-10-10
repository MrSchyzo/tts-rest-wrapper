#!/bin/bash

pushd "$(dirname "$(realpath "$0")")" || exit

>&2 echo "Generating shared volume directory in replicas-local-deploy"
mkdir -p replicas-local-deploy/store-cache || exit

if [[ ! -f config/application.properties ]] ; then
  >&2 echo "Replicating config/application.properties.example to config/application.properties"
  cp config/application.properties.example config/application.properties || exit
fi

>&2 echo "Regenerating Dockerfile and docker-compose.yml from your current working directory, UID, and GID: $(id -u):$(id -g)"
if [[ -f replicas-local-deploy/Dockerfile ]] ; then
  rm replicas-local-deploy/Dockerfile || exit
fi
if [[ -f docker-compose.yml ]] ; then
  rm docker-compose.yml || exit
fi

sed 's/##USER##/'"$(id -u)"'/g' replicas-local-deploy/Dockerfile.template > replicas-local-deploy/Dockerfile || exit
sed 's/##USER##/'"$(id -u)"'/g' replicas-local-deploy/docker-compose.yml.template > docker-compose.yml || exit
sed -i 's/##GROUP##/'"$(id -g)"'/g' docker-compose.yml || exit
sed -i 's|##ABS_PATH##|'"$(pwd)"'|g' docker-compose.yml || exit

>&2 echo "docker-compose.yml is now generated in $(pwd)"

>&2 echo "Generating java nativeapp and docker image"
./mvnw package -Pnative -Dquarkus.native.container-build=true || exit
docker build -f replicas-local-deploy/Dockerfile -t quarkus/tts-rest-wrapper . || exit

>&2 echo "Now you can execute 'docker-compose up --scale tts-service=<N> [--other-options]'"

>&2 popd || exit
