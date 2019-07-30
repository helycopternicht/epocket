FROM openjdk:8-jdk
WORKDIR /app
COPY server/build/libs/server-0.0.1-SNAPSHOT.jar /app/server-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar","/app/server-0.0.1-SNAPSHOT.jar"]