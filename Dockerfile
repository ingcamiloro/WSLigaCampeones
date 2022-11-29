FROM openjdk:8-jdk-alpine
RUN mkdir -p  ~/applications/config/EAF/CO_Claro_IntCus_EAF_Domain_PR/config/WSLigaCampeones
WORKDIR /applications/config/EAF/CO_Claro_IntCus_EAF_Domain_PR/config/WSLigaCampeones
#COPY target/*.war /app/
COPY WSLigaCampeones/ /applications/config/EAF/CO_Claro_IntCus_EAF_Domain_PR/config/WSLigaCampeones/
#CMD ["java","-jar","/app/WSLigaCampeones-0.0.1-SNAPSHOT.war"]
