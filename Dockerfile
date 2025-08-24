# Build the application
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Run and Log the application
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
COPY src/main/resources/logback.xml logback.xml
ENV LOG_LEVEL=DEBUG
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]