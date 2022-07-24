FROM openjdk:18-jdk-alpine
EXPOSE 8081
ADD target/netology-0.0.1-SNAPSHOT.jar prodapp.jar
ENTRYPOINT ["java","-jar","/prodapp.jar"]