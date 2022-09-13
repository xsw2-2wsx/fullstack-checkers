FROM ubuntu:20.04

RUN apt-get update
RUN apt-get upgrade -y

RUN apt-get install openjdk-11-jdk -y

ADD app/build/libs/app.jar /

CMD ["java", "-jar", "app.jar"]