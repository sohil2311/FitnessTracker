# Use Maven to build the application
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Ensure COPY command points to the correct directory
COPY FitnessTracker-backend/pom.xml .  
RUN mvn dependency:go-offline

COPY FitnessTracker-backend/ .  
RUN mvn clean package -DskipTests

# Use a smaller JDK image to run the application
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/FitnessTracker-backend-0.0.1-SNAPSHOT.jar /app/FitnessTracker-backend.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/FitnessTracker-backend.jar"]


