FROM openjdk:21

WORKDIR /app

COPY target/FitnessTracker-backend-0.0.1-SNAPSHOT.jar /app/FitnessTracker-backend-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "FitnessTracker-backend-0.0.1-SNAPSHOT.jar"]
