# ---- Build stage ----
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .
RUN mvn -B -q dependency:go-offline

COPY src ./src
RUN mvn -B clean package -DskipTests


# ---- Runtime stage ----
FROM eclipse-temurin:21-jre

# Create non-root user
RUN groupadd --system spring && \
    useradd --system --gid spring --create-home --home-dir /app spring

WORKDIR /app

# Copy jar and set ownership
COPY --from=builder /app/target/media-asset-annotator-server.jar app.jar
RUN chown spring:spring app.jar

# Switch to non-root user
USER spring

ENTRYPOINT ["java", "-jar", "app.jar"]
