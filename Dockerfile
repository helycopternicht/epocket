FROM openjdk:8-jdk
WORKDIR /app
COPY server/target/server-0.0.1-SNAPSHOT.jar /app/server/target/server-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar","/app/server/target/server-0.0.1-SNAPSHOT.jar"]