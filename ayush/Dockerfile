# Stage 1: Build the Spring Boot application
FROM maven:3.9-eclipse-temurin-23 AS builder

WORKDIR /app

# Copy Maven configuration
COPY pom.xml .
COPY .mvn/ .mvn/
COPY mvnw .

RUN chmod +x mvnw

# Copy backend source code only
COPY src/ src/

# Build the Spring Boot JAR
RUN ./mvnw -B -DskipTests package

# Stage 2: Run the application
FROM eclipse-temurin:23-jre

WORKDIR /app

# Copy the packaged JAR
COPY --from=builder /app/target/*.jar /app/app.jar

# Render web service port
EXPOSE 10000

# Start Spring Boot on Render's assigned port
CMD ["sh", "-c", "java -Dserver.port=${PORT:-10000} -jar /app/app.jar"]