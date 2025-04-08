# Build stage
FROM maven:3.9.9-amazoncorretto-21-alpine AS build
WORKDIR /usr/portfolio-back
COPY src src
COPY pom.xml .
RUN mvn -f pom.xml clean package -Dmaven.test.skip

# Package stage
FROM amazoncorretto:21-alpine-jdk
WORKDIR /usr/portfolio-back
COPY .env .
COPY --from=build /usr/portfolio-back/target/portfolio-back-1.0.0-SNAPSHOT.jar portfolio-back.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "portfolio-back.jar"]
