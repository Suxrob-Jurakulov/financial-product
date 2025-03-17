# Stage 1: Build the project using Maven
FROM maven:3.9.9-eclipse-temurin-17 AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy all project files into the container
COPY . .

# Build the project and skip tests to speed up the process
RUN mvn clean package -DskipTests

# Stage 2: Create a lightweight runtime image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy only the built JAR file from the previous stage
COPY --from=builder /app/target/financial-product-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that the Spring Boot application runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]