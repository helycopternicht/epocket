# HOW TO INSTALL
### Requirements
To start project you need:
 - jdk >= 8
 - docker
 - docker-compose

### Step 1. 
Clone project and enter project directory
```
git clone https://github.com/helycopternicht/epocket.git
cd epocket
```
### Step 2.
Compile and install 
```
./mvnw clean install -Dmaven.test.skip=true 
```
We skip tests is because integration tests require database, which we have not yet 

### Step 3. 
Build server app
```
docker-compose up --build
```
wait until projects start.....

May need to restart app service, because app service starts earlier tha database service
```
docker-compose start app
```

### Step 4.
Build and start client app with settings.
Set to corresponding env variables values to start client with.
For exmaple:
```
-Dclient-settings.number-of-users=10
-Dclient-settings.number-of-user-threads=5
-Dclient-settings.number-of-rounds-per-thread=5
```

```
cd client
./mvnw package
java -Dclient-settings.number-of-users=5 -Dclient-settings.number-of-user-threads=5 -Dclient-settings.number-of-rounds-per-thread=5 -jar client/target/client-0.0.1-SNAPSHOT.jar
```
We've done it.
Instruction requires linux based OS. For other operation systems may need some changes