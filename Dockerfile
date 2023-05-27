FROM openjdk:17
ADD /target/backendExchange-0.0.1-SNAPSHOT.jar javaBinance.jar
ENTRYPOINT ["java", "-jar", "javaBinance.jar"]