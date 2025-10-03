# ---------- Build Stage ----------
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml first so dependencies can be cached
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Now copy the source
COPY src ./src

# Package the application (skip tests to make build faster)
RUN mvn clean package -DskipTests

# ---------- Runtime Stage ----------
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copy the fat JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Render will inject its own PORT env var
EXPOSE 8080
ENTRYPOINT ["java", "-Dserver.port=${PORT}", "-jar", "app.jar"]
