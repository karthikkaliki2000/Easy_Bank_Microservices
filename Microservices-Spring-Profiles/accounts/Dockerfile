#start with a base image conatining java runtime
#   imagename:tag
#FROM openjdk:17-jdk-slim
FROM eclipse-temurin:17-jdk-alpine


#MAINTAINER eazybank.com  --->deprecated
LABEL maintainer="eazybank.com"
#who is maintained this image

COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar
#Telling to docker to copy the jar file in target folder of current application as accounts into current/root folder of docker image after creation of docker image

ENTRYPOINT ["java","-jar","accounts-0.0.1-SNAPSHOT.jar"]
#whenever anyone creating a container from my docker image then it tells to docker please execute this command as entry point for running my jar

