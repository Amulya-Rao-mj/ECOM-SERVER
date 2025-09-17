
# -------- Build Stage --------
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy only pom.xml first (to leverage Docker cache for dependencies)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Now copy the rest of the project
COPY src ./src

# Package the app (skip tests for faster builds)
RUN mvn clean package -DskipTests

# -------- Runtime Stage --------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy only the built jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]

