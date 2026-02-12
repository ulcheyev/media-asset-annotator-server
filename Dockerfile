FROM openjdk:21-jdk-slim

RUN apt-get update && \
    apt-get install -y maven

WORKDIR /app

COPY . /app

RUN mvn clean package -DskipTests

CMD ["java", "-jar", "target/csat-hangar-maintenance-planning-system-0.0.1-SNAPSHOT.jar"]