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
./gradlew clean build install -x test
```
We skip tests is because integration tests require database, which we have not yet 

### Step 3. 
Build server app
```
docker-compose up --build
docker-compose ps
```
If the app is not started than need to restart app service, because app service starts earlier than database service
```
docker-compose start app
```

### Step 4.
Start client app with settings.
Set to corresponding env variables values to start client with.
For example:
```
-Dclient-settings.number-of-users=10
-Dclient-settings.number-of-user-threads=5
-Dclient-settings.number-of-rounds-per-thread=5
```

```
java -Dclient-settings.number-of-users=5 -Dclient-settings.number-of-user-threads=5 -Dclient-settings.number-of-rounds-per-thread=5 -jar client/build/libs/client-0.0.1-SNAPSHOT.jar
```
We've done it.
Instruction requires linux based OS. For other operation systems may need some changes