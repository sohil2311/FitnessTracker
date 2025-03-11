# Stage 1: Build the JAR file
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Copy project files
COPY . .

# Grant execute permissions to Maven Wrapper
RUN chmod +x mvnw

# Build the application without running tests
RUN ./mvnw clean package -DskipTests

# Stage 2: Create the final image
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port your app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
