
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

COPY . .

RUN mvn clean package

# =========================
# Stage 2 - Runtime Stage
# =========================
FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

RUN apt-get update && apt-get install -y \
    libxext6 \
    libxrender1 \
    libxtst6 \
    libxi6 \
    x11-apps

ENV DISPLAY=host.docker.internal:0
ENV JAVA_TOOL_OPTIONS="-Djava.awt.headless=false"


CMD ["java", "-jar", "app.jar"]