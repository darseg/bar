FROM openjdk:11
MAINTAINER darseg
COPY target/bar-0.0.1-SNAPSHOT.jar /opt/bar.jar
ENTRYPOINT ["java","-jar","/opt/bar.jar"]