# Stage 1: Build the JAR file
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app
COPY . .
RUN ./mvnw clean package

# Stage 2: Create the final image
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/FitnessTracker-backend-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]