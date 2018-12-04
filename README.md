## System integration based on message broker pattern

The project was created to present message broker[1] enterprise integration pattern. The following elements are used:

- message broker - activemq is used
- message producer - spring boot application with jms support
- message consumer - spring boot application with jms support

The following tools are required to work with the project:

 - jdk8 or later
 - maven 3.3.9 or later
 - [optional] docker and docker compose
 
Producer publishes stock information with fixed interval. The behaviour of the application can be configured via .env file. The following properties can be modified: 
 
 
## External sources
[1] https://www.enterpriseintegrationpatterns.com/patterns/messaging/MessageBroker.html
