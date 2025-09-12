FROM openjdk:17-jdk-slim

LABEL maintainer="Angela"

WORKDIR /app

COPY target/*.jar app.jar

ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["java","-jar","app.jar"]
