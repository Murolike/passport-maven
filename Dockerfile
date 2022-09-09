FROM eclipse-temurin:11

RUN apt-get update && apt-get install -y bzip2 sqlite3 pgloader
WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY src ./src
CMD /app

#COPY target/passportService-*.jar passportService.jar
#ENTRYPOINT ["java","-jar","passportService.jar"]
