FROM eclipse-temurin:17-jre

WORKDIR /app

COPY target/classes ./classes
COPY target/dependency ./dependency

ENTRYPOINT ["java", "-cp", "classes:dependency/*", "de.fherfurt.devops.Main"]