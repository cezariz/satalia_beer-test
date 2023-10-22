# Build stage
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests

# Package stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/beer-test-0.0.1-SNAPSHOT.jar /app/beer-test.jar
CMD ["java", "-jar", "/app/beer-test.jar"]
