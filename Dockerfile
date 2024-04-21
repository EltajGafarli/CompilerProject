FROM ubuntu:20.04 AS builder

WORKDIR /workspace/app

# Install dependencies and utilities
RUN apt-get update --fix-missing && \
    apt-get install -y --no-install-recommends openjdk-17-jdk wget unzip curl && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Continue with the rest of your Dockerfile
COPY gradlew .
RUN chmod +x ./gradlew
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

# Build your application
RUN ./gradlew build -x test --info --stacktrace

FROM openjdk:17-jdk
VOLUME /tmp
COPY --from=builder /workspace/app/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]


