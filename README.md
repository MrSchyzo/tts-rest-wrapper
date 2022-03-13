# tts-rest-wrapper Project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .


## How to use

### Swagger

Just `curl http://localhost/swagger`, it'll download a `swagger.yaml` file.

### API

#### `POST /speak`

Input as follows with HTTP header `Content-Type: application/json`
```json
{
  "text": <string>,
  "isMale": <boolean>
}
```

Output will be the URL of the generated mp3 file. 

#### `GET /speak/<code>`

Just `curl` to `http://localhost:80/speak/<someCode>`, it'll try to return an `octet stream`
if it happens to have a file corresponding to that code.

#### Bash oneliner

An oneliner that immediately plays the generated speech (assuming you have `mpv` installed).

```shell
curl \
  -H 'Content-Type: application/json'\ 
  -X POST http://localhost/speak \
  --data-raw '{"text": "Ciao", "isMale": false}' \
  | xargs mpv
```


## Adding git hooks for this project

Run this command in the repository root
```shell script
git config --local core.hooksPath suggested_hooks
```


## Custom configuration for this application

There is already an `application.properties` shipped in the classpath, but it can be overwritten
by creating a `config/application.properties` file into the working directory in which you wish to run the application.

For more info, check [this link](https://quarkus.io/guides/config-reference#configuration-sources).


## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.


## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.


## Generating a docker-compose with load-balanced replicas

In the repository root, run
```shell script
./generate-replicas-local-deploy.sh
```

It:
- generates `replicas-local-deploy/Dockerfile` that is used to build the `docker`ized application
- generates `docker-compose.yml` in order to run the load-balanced replicas under `nginx` 
- copies `config/application.properties.example` into `config/application.properties` if this one is not present yet
- creates `replicas-local-deploy/store-cache` directory that is used to store the shared volume of the replicas
- builds the code into a nativeapp
- builds the image using the generated `Dockerfile`

In order to `run` the replicas:
- `docker-compose up --scale tts-service=5` runs the replicas in "interactive mode"
- `docker-compose up --detach --scale tts-service=5` runs the replicas in "background mode"

In order to `stop` the replicas:
- `docker-compose down` if running the "background mode"
- `CTRL+C` if running the "interactive mode"


## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/tts-rest-wrapper-0.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.html.


## Related Guides

- Kotlin ([guide](https://quarkus.io/guides/kotlin)): Write your services in Kotlin


## Provided Code

### RESTEasy JAX-RS

Easily start your RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started#the-jax-rs-resources)
