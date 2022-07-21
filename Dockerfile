FROM openjdk:8-jdk-alpine

#RUN apk add bzip2
#WORKDIR /app
#
#COPY .mvn/ .mvn
#COPY mvnw pom.xml ./
#RUN ./mvnw dependency:go-offline
#COPY src ./src

COPY target/passportService-*.jar passportService.jar
ENTRYPOINT ["java","-jar","/passportService.jar"]