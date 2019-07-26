# HOW TO INSTALL

Step 1. Clone project and enter project directory
git clone https://github.com/helycopternicht/epocket.git
cd epocket

Step 2. Build server app
docker-compose up --build

wait until projects starts.....

Step 3. Install interface
cd interface
./mvnw install 

Step 4. Build and start client app with settings
cd ../client
./mvnw package

Set to corresponding env variables values and start client
java -Dclient-settings.number-of-users=5 -Dclient-settings.number-of-user-threads=5 -Dclient-settings.number-of-rounds-per-thread=5 -jar client/target/client-0.0.1-SNAPSHOT.jar