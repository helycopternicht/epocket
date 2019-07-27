# HOW TO INSTALL

###Step 1. 
Clone project and enter project directory
```
git clone https://github.com/helycopternicht/epocket.git
cd epocket
```

###Step 2. 
Build server app
```
docker-compose up --build
```

wait until projects start.....

###Step 3.
Compile and install 
```
mvn install -D maven.test.skip=true 
```

###Step 4.
Build and start client app with settings.
Set to corresponding env variables values and start client for exmaple
-Dclient-settings.number-of-users=5
-Dclient-settings.number-of-user-threads=5
-Dclient-settings.number-of-rounds-per-thread=5

```
cd client
./mvnw package
java -Dclient-settings.number-of-users=5 -Dclient-settings.number-of-user-threads=5 -Dclient-settings.number-of-rounds-per-thread=5 -jar client/target/client-0.0.1-SNAPSHOT.jar
```

Instruction requires linux based OS. For other operation systems may need some changes





