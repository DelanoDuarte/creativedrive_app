FROM openjdk:8-alpine

MAINTAINER Delano Junior

RUN apk update && apk add bash

RUN mkdir -p /opt/app

ENV PROJECT_HOME /opt/app

COPY target/creative_drive_app.jar $PROJECT_HOME/creative_drive_app.jar

WORKDIR $PROJECT_HOME

CMD ["java", "-jar", "-Dspring.profiles.active=prod" ,"./creative_drive_app.jar"]