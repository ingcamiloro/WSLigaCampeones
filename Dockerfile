FROM openjdk:8-jdk-alpine
COPY target/*.war /app/
CMD ["java","-jar","/app/WSLigaCampeones-0.0.1-SNAPSHOT.war"]
