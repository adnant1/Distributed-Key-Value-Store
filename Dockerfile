# === Build stage ===
FROM maven:3.9.4-eclipse-temurin-21 AS build 

WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml ./
RUN mvn dependency:go-offline

# Copy the source code
COPY src ./src

# Package the application
RUN mvn clean package 

# === Runtime stage ===
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copy the packaged application from the build stage
COPY --from=build /app/target/distributed-key-value-store-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]