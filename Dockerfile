FROM openjdk:17-jre-slim
WORKDIR /app
COPY /target/beer-test-0.0.1-SNAPSHOT.jar /app/beer-test.jar
CMD ["java", "-jar", "/app/my-app.jar"]
