FROM openjdk:8-jdk
WORKDIR /app
COPY . .
RUN ./mvnw clean package -Dmaven.test.skip=true
#COPY server/target/server-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar","/app/server/target/server-0.0.1-SNAPSHOT.jar"]
