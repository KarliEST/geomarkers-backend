# GEOMARKERS PROJECT

### Project author: Karli Kullasepp.

# Read Me
Backend for a webapp that displays/adds/removes/updates interactive markers on map. 
Project creates required endpoints for frontend and stores point data in database.
Requests are made in GeoJSON format and responses come in GeoJSON format, except DELETE (see below).
Validation checks if incoming JSON is in GeoJSON format.

* Programming language is Java 17.
* Uses Spring Framework (v3.1.4).
* Uses Gradle for dependencies.
* Conf is in [application.properties](src/main/resources/application.properties).
* Point data is received and returned in GeoJson format.
* PostgreSQL was used as database.

Project has total of 5 endpoints, defined
in [LocationController](src/main/java/com/locator/backend/controllers/LocationController.java)
in [controller](src/main/java/com/locator/backend/controllers) package.<br/>
<br/>
POST/PUT requests MUST be in valid GeoJSON format! <br/>
GeoJSON MUST contain a feature that has "type": "Point", properties object MUST contain "description" property and
geometry object MUST contain coordinates array. <br/>
POST request to "/post" endpoint adds new pint object to database and returns that points data from database in GeoJSON
format. <br/>
PUT request with valid "id" and updated GeoJSON body to "/put?id={id}" endpoint updates corresponding data object and
responds with updated GeoJSON.<br/>
DELETE request with valid "id" to "/delete?id={id}" endpoint removes data object with corresponding id. <br/>
GET request with valid "id" to "/get?id={id}" endpoint responds with point data in GeoJSON format of corresponding
id. <br/>
GET request to "/all" endpoint responds with all point's data in GeoJSON format.

Services are located in [services](src/main/java/com/locator/backend/services) package.

# Getting Started

Before you start the app you have to set up your database.

* If database is set up, you can run SQL scripts from [tables.sql](src/main/resources/tables.sql) in resources
  folder to generate table.

* Before running app, change database's datasource user and password
  in [application.properties](src/main/resources/application.properties). <br/>
  Default user/password is "postgres"/"password" to your postgres database user/password.

* Run [BackendApplication](src/main/java/com/locator/backend/BackendApplication.java)

### NB! Front end is configured to use proxy port: 8080
