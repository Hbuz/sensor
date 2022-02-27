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

* Creating an executable JAR
Execute this command from the root of the project:
```
mvn install
```

### Run the application
Execute this command from the root of the project:
```
mvn spring-boot:run
```
or as a packaged jar application
```
java -jar target/sensor-0.0.1-SNAPSHOT.jar
```

### Endpoints implemented
* Save temperature data
```
POST - http://localhost:8080/api/v1/temperature

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
GET - http://localhost:8080/api/v1/temperature?mode=HOUR

* The request parameter must be one of: HOUR , DAY
```


I also added a postman collection that can be imported and used in Postman to test the application: 

```Sensor API.postman_collection.json```



