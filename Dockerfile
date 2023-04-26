FROM openjdk:17
ADD /target/backendExchange-0.0.1-SNAPSHOT.jar backend.jar
ENTRYPOINT ["java", "-jar", "backend.jar"]