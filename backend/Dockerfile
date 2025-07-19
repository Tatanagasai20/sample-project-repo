# Stage 1: Build the application
FROM maven:3.9-eclipse-temurin-17 as build

# Set working directory
WORKDIR /app

# Copy the Maven POM file
COPY pom.xml .

# Download all required dependencies into one layer
# This is done in a separate step to leverage Docker layer caching
RUN mvn dependency:go-offline -B

# Copy the project source
COPY src ./src

# Build the application
RUN mvn package -DskipTests

# Stage 2: Create the runtime image
FROM eclipse-temurin:17-jre-alpine

# Set working directory
WORKDIR /app

# Create directory for file uploads
RUN mkdir -p /app/uploads

# Copy the built JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Environment variables
ENV SPRING_PROFILES_ACTIVE=prod

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]