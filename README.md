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

It is important to remember that, the execution of these scripts will happen in alphabetical order. 
Once both the scripts are ready, we can add them to /docker-entrypoint-initdb.d under volumes 
section of docker-compose.yml. By default all scripts under this path executes in alphabetical order
during container configuration.  

### Creating Spring Boot Container
In order to create a docker container for our spring boot application, we first need to have a 
Dockerfile which can be used to build an image for the application. Later we can use this image in 
docker-compose.yml to create containers.

In the same docker-compose.yml, we can connect our application container with RabbitMQ and MySQL 
container using environment variables. 

### Execution
This is the easiest part. Go to the root of the project and run below commands.
```
cd src/main/resources/docker
docker-compose up
```  
### Other Useful Commands
  * Build docker image: `docker build -t spring-docker-compose -f ./src/main/resources/docker/Dockerfile .`