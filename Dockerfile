FROM openjdk:8-jdk-alpine
COPY target/*.jar /app/
CMD ["java","-jar","/app/WSLigaCampeones-0.0.1-SNAPSHOT.jar"]
