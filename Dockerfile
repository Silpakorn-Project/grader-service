FROM openjdk:21-jdk

WORKDIR /home/app

COPY target/grader*.jar app.jar

EXPOSE 5000

ENTRYPOINT exec java -jar app.jar
