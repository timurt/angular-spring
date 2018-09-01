FROM java:openjdk-8-jdk-alpine

WORKDIR /app

ADD . /app

CMD mvn package
CMD java -jar target/arm-0.0.1-SNAPSHOT.jar --spring.profiles.active=h2

EXPOSE 8888