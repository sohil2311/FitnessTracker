# Use Maven to build the application
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy only the POM file and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the rest of the application source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Use a smaller JDK image to run the application
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/FitnessTracker-backend-0.0.1-SNAPSHOT.jar /app/FitnessTracker-backend.jar

# Expose the application port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "/app/FitnessTracker-backend.jar"]