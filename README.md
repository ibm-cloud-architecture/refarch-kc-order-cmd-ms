# Order management microservice


A simple Order manager for the reference implementation of IBM Event-driven architecture. This implementation is based on quarkus, reactive messaging and
support the SAGA choerography demonstration.

This service manage the Saga.

Read more about the detail implementation [on this site](https://ibm-cloud-architecture.github.io/eda-saga-choreography)
## Running the application in dev mode

Start the docker compose (`docker-compose-dev.yaml`) from the project: [eda-saga-choreography](https://github.com/ibm-cloud-architecture/eda-saga-choreography)


You can run your application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
# or
quarkus dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging 

```sh
./scripts/buildAll.sh
```
