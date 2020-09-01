FROM openjdk:11-jre-slim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} task.jar
ENTRYPOINT ["java","-jar","/task.jar"]