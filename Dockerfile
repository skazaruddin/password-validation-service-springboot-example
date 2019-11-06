FROM openjdk:8-jdk-alpine

LABEL maintainer="azaruddin@sdrc.co.in"

EXPOSE 8080

ADD target/password-validation-service.jar password-validation-service.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/password-validation-service.jar"]
