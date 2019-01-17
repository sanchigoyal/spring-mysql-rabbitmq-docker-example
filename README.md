# Spring Boot Docker Container Integration With RabbitMQ and MySQL
This project demonstrates creating docker containers for spring boot application, rabbitmq and mysql. 
Later it demonstrates the use of docker-compose to integrate these containers.

## Prerequisites
 * docker
 * docker-compose

## About Application
It is a simple messaging application. That -
 * Listens to a RabbitMQ queue
 * Reads the message when available
 * Stores it in the MySQL database

### Rabbit MQ Configuration
 * Queue Name: message-queue
 * Vhost: /
 * Durable: true

### MySQL Schema
* Schema Name: message 
* Table Name: message

| id | sender           | receiver           | timestamp           | message_text  |
|----|------------------|--------------------|---------------------|---------------|
| 1  | sender@gmail.com | receiver@gmail.com | 2018-12-09 18:41:38 | Hello World !!|

### Message Structure
```JSON
{
  "sender": "sender@gmail.com",
  "receiver": "receiver@gmail.com",
  "timestamp": "2018-12-09T18:41:37.615",
  "messageText": "Hello World !!"
}
```
### Creating RabbitMQ Container with Static Configuration
You can always create a RabbitMQ container using the official RabbitMQ docker images available on
[docker hub](https://hub.docker.com/r/library/rabbitmq/). In this case I have used 
rabbitmq:3-management. This adds RabbitMQ management console which is exposed on port 15672. In this
example the console is used to drop message into the queue.

```
rabbitmq-container:
  image: rabbitmq:3-management
  ports:
    - 5672:5672
    - 15672:15672
  volumes:
    - ./rabbitmq/definitions.json:/etc/rabbitmq/definitions.json:ro
    - ./rabbitmq/rabbitmq.config:/etc/rabbitmq/rabbitmq.config:ro
```

However, for this example we also need a queue with name 'message-queue' pre-configured in the RabbitMQ
server. Follow the steps listed on [Deploy RabbitMQ with Docker-static configuration](https://medium.com/@thomasdecaux/deploy-rabbitmq-with-docker-static-configuration-23ad39cdbf39)
to have the queue created during RabbitMQ container setup.

### Creating MySQL Container with Static Configuration
Similar to RabbitMQ, MySQL container can be created using the official MySQL docker image available
on [docker hub](https://hub.docker.com/r/library/mysql/). You can install MySQL Workbench for viewing 
data on MySQL server. 

Also, for this example we need a table named 'message' pre-configured in MySQL database. In order
to do that, we will create two scripts - 
1. Create schema 
2. Create table 'message' under our schema

```
mysql-container:
  image: mysql
  ports:
    - 3306:3306
  environment:
    - MYSQL_ROOT_PASSWORD=root
  volumes:
    - ./mysql:/docker-entrypoint-initdb.d:ro
```

It is important to remember that, the execution of these scripts will happen in alphabetical order. 
Once both the scripts are ready, we can add them to /docker-entrypoint-initdb.d under volumes 
section of docker-compose.yml. By default all scripts under this path executes in alphabetical order
during container configuration.  

### Creating Spring Boot Container
In order to create a docker container for our spring boot application, we first need to have a 
Dockerfile which can be used to build an image for the application. 

```
# Start with a base image containing Java runtime
FROM openjdk:8-jdk-alpine

# Add Maintainer Info
LABEL maintainer="sanchi.goyal.sg@gmail.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8085 available to the world outside this container
EXPOSE 8085

# The application's jar file
ARG JAR_FILE=./build/libs/spring-mysql-rabbitmq-docker-example-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} spring-mysql-rabbitmq-docker-example.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/spring-mysql-rabbitmq-docker-example.jar"]
```

Now that we have the setup available to create a Docker image, lets use it in docker-compose.yml to 
create a container and assign RabbitMQ and MySQL as dependencies. 

```
app-container:
  build:
    context: ../../../../
    dockerfile: ./src/main/resources/docker/Dockerfile
  ports:
    - 8085:8085
  environment:
    - SPRING_DATASOURCE_URL=jdbc:mysql://mysql-container:3306/message?useSSL=false&allowPublicKeyRetrieval=true
    - SPRING_DATASOURCE_USERNAME=root
    - SPRING_DATASOURCE_PASSWORD=root
    - SPRING_RABBITMQ_HOST=rabbitmq-container
  depends_on:
    - rabbitmq-container
    - mysql-container
```

### Execution
This is the easiest part. Go to the root of the project and run below commands.
```
cd src/main/resources/docker
docker-compose up
```  
### Other Useful Commands
  * Build docker image: `docker build -t spring-docker-compose -f ./src/main/resources/docker/Dockerfile .`