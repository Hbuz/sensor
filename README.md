# sensor
API to save and retrieve temperature data

## Getting Started


### Main technologies used
 - JDK 11
 - Maven
 - Spring Boot
 - H2 DB


### Installing
* Clone the repository
```
git clone git@github.com:Hbuz/sensor.git
```

* Go to root project
```
cd sensor
```

* Creating an executable JAR
Execute this command from the root project:
```
./mvnw package
```

### Run the test
Execute this command from the root project:
```
./mvnw test
```

### Run the application
Execute this command from the root project:
```
java -jar target/*.jar
```

### Endpoints implemented
* Save temperature data
```
POST - http://localhost:8080/api/temperature

The body is different depending on the type or request: single value or bulk of values

* Single temperature value body request *
{ 
	"value": 19.5
}

* Temperature values in bulk body *
[
    {
    	"timestamp": "2022-02-27T10:39:12",
        "value": 21.2
    },
    {
    	"timestamp": "2022-02-27T19:44:32",
        "value": 18.7
    },
    {
    	"timestamp": "2022-02-27T19:50:11",
        "value": 15.2
    }
]
```

* Retrieve the aggregated temperature data
```
GET - http://localhost:8080/api/temperature?mode=HOUR

* The request parameter must be one of: HOUR , DAY
```


### Postman
A postman collection has been added to the root project. It can be imported and used in Postman to test the application: 

```Sensor API.postman_collection.json```


### Swagger Docs
```
http://localhost:8080/swagger-ui.html#/
```


