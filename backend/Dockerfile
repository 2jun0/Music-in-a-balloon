# Use an official Gradle image to build the application
FROM gradle:8.9.0-jdk21 AS build

# Set the working directory
WORKDIR /app

# Copy the Gradle wrapper and the build.gradle files
COPY gradle /app/gradle
COPY gradlew build.gradle settings.gradle ./

# Download dependencies
RUN ./gradlew dependencies --no-daemon

# Copy the source code and build the application
COPY src ./src
RUN ./gradlew build -x test --no-daemon

# Use an OpenJDK runtime image
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built jar file from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Define the command to run the application
CMD ["java", "-jar", "app.jar"]
