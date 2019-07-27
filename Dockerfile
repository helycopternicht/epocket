FROM maven:3.6.1-jdk-8
WORKDIR /app
COPY server/target/server-0.0.1-SNAPSHOT.jar /app/server/target/server-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar","/app/server/target/server-0.0.1-SNAPSHOT.jar"]