# LogProcessor
A RESTful Web Service to consume, store, and process logs

## About
This project has been developed using Spring Boot, Java 11 and Elastic Search (6.7.0) to provide a RESTful API for a processing logs.

Once the application is running, Swagger documentation can be used to interact with the rest api:
http://localhost:8098/swagger-ui.html

<img src="/images/swagger.PNG" height=200> 


## Instructions to Run

### Step 1 Download the code from github
```git clone  ```

### Step 2 Build the project
```mvn clean install```

### Step 3 Install Elastic Search 6.7.0 
https://www.elastic.co/downloads/past-releases/elasticsearch-6-7-0

### Step 4 Start the Elastic search server from cmd
.\bin\elasticsearch.bat

### Step 5 Run the project
    mvn spring-boot:run

## Solution

Entities: UserLog, Action and Properties.
DataBase: ElasticSearch (NoSql)
Operations:
  * Post: Add a log -> Response (201 CREATED)
  * Get:  Search for logs given the parameters (userId, logtype and date) -> Response (200 SUCCESS)

### Constraint
    The logs should be retrievable by any combination of user, time and log type. The response should be a list of logs.
    
 For the above mentioned constraint, the get operation accepts a query parameter of userId, time and log type. These parameters are optional. If none of the paramters are passed to the endpoint then by default the sytem will give 5 logs as the output. The reason to limit the logs if no parameter is found is because system could contain billions of logs, therefore limiting it to a particular size.
     
### Cloud Scalable
The reason to use elastic search for this usecase, is because elasticsearch is a search engine built on top of lucene. It is also designed to scale horizontally. We can use Amazon Elasticsearch Service to deploy, operate, and scale Elasticsearch in the AWS Cloud.

## PostMan Screenshots

<img src="/images/addlog.PNG" height=200>           <img src="/images/searchByParam.PNG" height=200>
