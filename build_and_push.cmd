call mvn clean install

docker build . -t jigokukozou/java-binance:2.0.0
docker push jigokukozou/java-binance:2.0.0
