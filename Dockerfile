# -------- Stage 1: Build with Maven --------
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml and download dependencies first (better caching)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code and build the project
COPY src ./src
RUN mvn clean package -Dmaven.test.skip=true

# -------- Stage 2: Runtime --------
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copy JAR from build stage
COPY --from=build /app/target/ecom_server-0.0.1-SNAPSHOT.jar app.jar

# Render injects PORT dynamically, so map it
ENV PORT=8080
EXPOSE 8080

# Start Spring Boot with Render's PORT
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=${PORT}"]

