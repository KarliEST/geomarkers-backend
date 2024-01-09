# GEOMARKERS PROJECT

### Project author: Karli Kullasepp.

# Read Me
Backend for a webapp that displays/adds/removes/updates interactive markers on map. 
Project creates required endpoints for frontend and stores location data for the marker in database.
Requests are made in GeoJSON format and responses come in GeoJSON format, except DELETE (see below).
Validation checks if incoming JSON is in GeoJSON format.

* Programming language is Java 17.
* Uses Spring Framework (v3.1.4).
* Uses Gradle for dependencies.
* Conf is in [application.properties](src/main/resources/application.properties).
* Point data is received and returned in GeoJson format.
* PostgreSQL was used as database.
* Includes dockerized database with persistence for development.

Project has total of 5 endpoints, defined
in [LocationController](src/main/java/com/locator/backend/controllers/LocationController.java)
in [controller](src/main/java/com/locator/backend/controllers) package. \
\
POST/PUT requests MUST be in valid GeoJSON format! \
GeoJSON MUST contain a feature that has "type": "Point", properties object MUST contain "description" property and
geometry object MUST contain coordinates array. \
POST request to "/post" endpoint adds new pint object to database and returns that points data from database in GeoJSON
format. \
PUT request with valid "id" and updated GeoJSON body to "/put?id={id}" endpoint updates corresponding data object and
responds with updated GeoJSON. \
DELETE request with valid "id" to "/delete?id={id}" endpoint removes data object with corresponding id. \
GET request with valid "id" to "/get?id={id}" endpoint responds with point data in GeoJSON format of corresponding
id. \
GET request to "/all" endpoint responds with all point's data in GeoJSON format.

Services are located in [services](src/main/java/com/locator/backend/services) package.

# Getting Started

### Setup with database
Before you start the app, you have to set up your database. Current default is PostgreSQL.

1. If database is set up, you can run SQL scripts from [database.sql](database/database.sql) in database
  folder to generate table.

2. Before running app, change database's datasource user and password
  in [application.properties](src/main/resources/application.properties). \
  Default user/password is "postgres"/"password" to your postgres database user/password.

3. Run [BackendApplication](src/main/java/com/locator/backend/BackendApplication.java)

<br/>

### Setup with dockerized database
**Docker must be running!** \
PostgreSQL database is used in docker containers and if needed Adminer as database manager. \
Data is stored on localhost! 
1. Change database's properties in [application.properties](src/main/resources/application.properties). \
* Comment out ln 4 (contains port nr 5432)
* Uncomment ln 7 (contains port nr 32768) 

2. To run database containers, open terminal in project folder and run: \
**docker-compose -f postgres.yaml up -d**

3. Run [BackendApplication](src/main/java/com/locator/backend/BackendApplication.java)
4. To stop and containers, networks, volumes, and images created by **up**, type into terminal: \
   **docker-compose -f postgres.yaml down**

To use Adminer open [postgres.yaml](postgres.yaml) and uncomment lines 20-24. \
By default Adminer uses port 8081 (http://localhost:8081/). Port nr can be changed on ln 24. \
To login (defined in [postgres.yaml](postgres.yaml)) :
* System: PostgreSQL
* Server: location-db
* Username: postgres
* Password: password
* Database: locations


### NB! Front end is configured to use proxy port: 8080



