FROM maven:3.9.9-eclipse-temurin-21 AS build

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

# ---------- stage 2: runtime ----------
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/furshet-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8000

ENTRYPOINT ["java", "-jar", "app.jar"]