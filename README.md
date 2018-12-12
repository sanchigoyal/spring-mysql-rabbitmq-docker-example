# Spring Boot Docker Container Integration With RabbitMQ and MySQL
This project demonstrates creating docker containers for spring boot application, rabbitmq and mysql. 
Later it demonstrates the use of docker-compose to integrate these containers.

## Prerequisites
 * docker
 * docker-compose

## About Application
It's a simple messaging application. 
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

### Creating MySQL Container with Static Configuration

### Creating Spring Boot Container

### Execution
This is the easiest part. Go to the root of the project and run below commands.
```
cd src/main/resources/docker
docker-compose up
```  
### Other Useful Commands
  * Build docker image: `docker build -t spring-docker-compose -f ./src/main/resources/docker/Dockerfile .`