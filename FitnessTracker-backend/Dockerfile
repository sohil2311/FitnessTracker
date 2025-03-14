# Use Maven to build the application
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml .  
RUN mvn dependency:go-offline

# Copy the source code and build the application
COPY . .  
RUN mvn clean package -DskipTests

# Use a smaller JDK image to run the application
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/app.jar"]
