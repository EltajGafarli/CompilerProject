FROM openjdk:17-jdk AS builder

COPY ./build/libs/SDPProject-0.0.1-SNAPSHOT.jar ./app/

WORKDIR /app/

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "SDPProject-0.0.1-SNAPSHOT.jar"]


