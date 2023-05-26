# JavaBinance

JavaBinance is a Spring Boot application for trading on the cryptocurrency exchange, which will allow the user to learn how to make transactions, analyze them and try various strategies without the risk of losing material assets.

## Deploy
- Install Java 17
- Install PostgreSQL
- Rename `application.origins.properties` to `application.properties`, set spring.datasource.password
- Create database with name `java_binance` or change `spring.datasource.url` in application.properties
- `psql -d java_binance -f init.sql`
- Run

Server should start on `localhost:8080`

## Screenshots

![Trading page](pic1.png)
![Balance page](pic2.png)
![History page](pic3.png)
