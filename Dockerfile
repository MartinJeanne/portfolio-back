# Build stage
FROM maven:3.9.9-amazoncorretto-21-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src src
RUN mvn -f pom.xml clean package -Dmaven.test.skip

# Package stage
FROM amazoncorretto:21-alpine-jdk
WORKDIR /app
COPY .env .
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
