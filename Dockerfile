# Build stage with Maven and Java 20 (since 23 is unavailable)
FROM maven:3.9.4-eclipse-temurin-20 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Runtime stage with Java 23 JDK
FROM eclipse-temurin:23-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
