FROM eclipse-temurin:25-jre

WORKDIR /app

COPY target/*.jar app.jar

RUN apt-get update && apt-get install -y \
    libxext6 \
    libxrender1 \
    libxtst6 \
    libxi6 \
    x11-apps

ENV DISPLAY=host.docker.internal:0
ENV JAVA_TOOL_OPTIONS="-Djava.awt.headless=false"

CMD ["java", "-jar", "app.jar"]