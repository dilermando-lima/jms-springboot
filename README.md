# JMS Spring boot with RABBITMQ and SQSAMAZON


  * [About](#about)
  * [Container RABBITMQ](#container-rabbitmq)
    + [Starting container](#starting-container)
    + [Adding new queues](#adding-new-queues)
  * [Switching RABBITMQ or SQSAMAZON on project](#switching-rabbitmq-or-sqsamazon-on-project)
  * [Run application and test queue consuming](#run-application-and-test-queue-consuming)

## About
Simple project has connections with RABBITMQ and SQSAMAZON covering listener and publish queues.

## Container RABBITMQ
All configuration RABBITMQ container is in `./local/container/*`

### Starting container

In order running RABBITMQ container up use code below:
```bash
docker-compose --env-file ./local/env/local.env  --file ./local/container/docker-compose.yml up # -d --build --force-recreate
```
Check rabbit console in http://localhost:15672/ after start container

### Adding new queues
All configurations rabbit is in `./local/container/rabbitmq/*`

In order to add new queue edit `./local/container/rabbitmq/definitions.json`


## Switching RABBITMQ or SQSAMAZON on project
It's possible change type of queue will be used on project by `.properties` variables.

Just change var bellow in `src/main/resources/application.properties`

```properties
queue.config.qualifier-conn=RABBIT
# queue.config.qualifier-conn=SQS_AMAZON
```

All logic for switching can be seing in `src/main/java/com/example/jmsspringboot/config/ConnectionQueueConfig.java`


## Run application and test queue consuming

After start spring boot application. try command below to publish messages and listener from queue by rest request:
```bash
curl --location --request POST 'localhost:8095/publish/NAME_QUEUE?HEADER_1=VALUE_HEADER' \
--header 'Content-Type: text/plain' \
--data-raw 'message to be published'
```
publish controller can be seing in `src/main/java/com/example/jmsspringboot/controller/PublisherController.java`

listener queue is placed in `src/main/java/com/example/jmsspringboot/service/MessageListener.java`

