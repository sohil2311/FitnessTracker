# Stage 1: Build the JAR file
FROM maven:3.8.6-openjdk-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run the JAR file
FROM openjdk:21
WORKDIR /app
COPY --from=build /app/target/FitnessTracker-backend-0.0.1-SNAPSHOT.jar /app/FitnessTracker-backend-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "FitnessTracker-backend-0.0.1-SNAPSHOT.jar"]