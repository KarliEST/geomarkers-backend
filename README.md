# GEOLOCATION PROJECT

### Project author: Karli Kullasepp.

# Read Me

* Programming language is Java 17.
* Uses Spring Framework (v3.1.4).
* Uses Gradle for dependencies.
* Conf is in [application.properties](src/main/resources/application.properties).
* Point data is received and returned in GeoJson format.

Project has total of 5 endpoints, defined
in [LocationController](src/main/java/com/locator/backend/controllers/LocationController.java)
in [controller](src/main/java/com/locator/backend/controllers) package.
Adding a point is done through sending a POST request at "/add" endpoint in GeoJson format.
GeoJson must contain a feature that has "type": "Point", properties object must contain "description" property and
geometry object must contain coordinates array.
Successful request is responded with response in GeoJson format. <br/>
Point's data is changed by sending PUT request to "/{id}" with GeoJson request.
Successful request is responded with response in GeoJson format. <br/>
Data removal is done through DELETE request using "/{id}" endpoint. Removal requires valid id. <br/>
GET "/{id}" returns single points data in GeoJson format. <br/>
GET "/get" returns all point's data in GeoJson format.

Services are located in [services](src/main/java/com/locator/backend/services) package.

# Getting Started

Before you start the app you have to set up your database.

* If database is set up, you can run SQL scripts from [tables.sql](src/main/resources/tables.sql) in resources
  folder to generate table.

* Run [BackendApplication](src/main/java/com/locator/backend/BackendApplication.java)

### NB! Front end is configured to use proxy port: 8080
