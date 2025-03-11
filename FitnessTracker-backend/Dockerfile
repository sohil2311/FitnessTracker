# Stage 1: Build the JAR file
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Grant execute permissions to mvnw
RUN chmod +x mvnw

# Build the JAR file
RUN ./mvnw clean package

# Stage 2: Create the final image
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/FitnessTracker-backend-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]