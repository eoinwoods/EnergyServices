FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
ARG SERVER_PORT

ADD ${JAR_FILE} app.jar

ENV JAVA_SERVER_PORT=$SERVER_PORT
# apparently changing the random number source speeds up Tomcat startup
ENTRYPOINT ["java", "-Dserver.port=${JAVA_SERVER_PORT}", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]
