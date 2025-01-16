FROM openjdk:21-jdk

WORKDIR /home/app

COPY target/compilerjava*.jar app.jar

EXPOSE 5000

ENTRYPOINT exec java -jar app.jar
