# Introduction
TwitterCLIApp is a simple Twitter client that allows users to post, show and delete tweets via command-line interface (CLI).
The architecture of this application follows the M~~V~~C (Model, ~~Viewer~~, Controller) pattern.
The classes except models and utilities are covered by unit & integration tests.
By implementing this project, I have been familiar with many key concepts in web development
such as HTTP, JSON, MVC, and Spring framework. I also get valuable hands-on experience in testing and refactoring code,
which will help me to develop larger applications in the future.

## Quick Start

### Requirements

- **JDK: >= 8**

- **Maven: >= 3.6.0** 

### Build
```sh
mvn clean package -DskipTests
```
### Run
```sh
java -jar target/java_apps-1.0-SNAPSHOT.jar [post | show | delete] [options]
```

### Usage


# Design

<img src="../../assets/tw_uml.png" alt="drawing"/>

## Components

<img src="../../assets/tw_dep.jpg" alt="drawing"/>

1. [**HttpHelper**](./src/main/java/ca/jrvs/apps/twitter/dao/helper):
Given a Uniform Resource Identifier (URI) and a method (either GET or POST),
the helper constructs, signs, and executes the corresponding HTTP request. 

2. [**Data Access Object (DAO)**](./src/main/java/ca/jrvs/apps/twitter/dao): 
The DAO component handles data with external storage, which is [Twitter REST API](https://developer.twitter.com/en/docs) 
in this application. It constructs URIs and sends the requests to the server with the help of `HttpHelper`,
then processes the HTTP response received. 

3. [**Service**](./src/main/java/ca/jrvs/apps/twitter/service): 
The service component handles the business logic of the application. In particular, it is responsible to check
if the input is valid, e.g., the status didn't exceed the maximum length allowed, the coordinates are within the range, etc. 

4. [**Controller**](./src/main/java/ca/jrvs/apps/twitter/controller):
The controller consumes user input `args[]` and calls the corresponding service layer method 
without doing anything related to business logic. 

5. [**Application**](./src/main/java/ca/jrvs/apps/twitter/TwitterCLIApp.java): 
On top of all layers, the application instantiates other components and trigger `run` method
which calls controller methods and print the response to command line.

## Model
<img src="../../assets/tw_model.png" alt="drawing"/>

Models are implemented with plain old Java object (POJO) which is a class with private member variables and public getter and setters. This class encapsulates Tweet data (Tweet objects) which often display in JSON format. 
The full version of the [Tweet object](https://developer.twitter.com/en/docs/tweets/data-dictionary/overview/tweet-object)
is complicated, hence we implemented a simplified one. Below is an example Tweet JSON: 

```json
{
   "created_at":"Mon Feb 18 21:24:39 +0000 2019",
   "id":1097607853932564480,
   "id_str":"1097607853932564480",
   "text":"test with loc223",
   "entities":{
      "hashtags":[],      
      "user_mentions":[]  
   },
   "coordinates":null,    
   "retweet_count":0,
   "favorite_count":0,
   "favorited":false,
   "retweeted":false
}
```

# Improvements
- at least three improvements

- at least three improvements

- at least three improvements
