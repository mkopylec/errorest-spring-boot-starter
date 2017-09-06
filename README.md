# ErroREST Spring Boot Starter
[![Build Status](https://travis-ci.org/mkopylec/errorest-spring-boot-starter.svg?branch=master)](https://travis-ci.org/mkopylec/errorest-spring-boot-starter)
[![Coverage Status](https://coveralls.io/repos/mkopylec/errorest-spring-boot-starter/badge.svg?branch=master&service=github)](https://coveralls.io/github/mkopylec/errorest-spring-boot-starter?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.mkopylec/errorest-spring-boot-starter/badge.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/com.github.mkopylec/errorest-spring-boot-starter)

Complete, ready to use error handling for Spring Boot REST services.

## Features
- unified HTTP response format for **every** unhandled exception (thrown from controllers or servlet filters)
- support for creating custom exceptions
- support for [Spring Security](https://projects.spring.io/spring-security/) exceptions
- support for handling external HTTP requests errors
- JSON and XML HTTP response body types

## Installing
```gradle
repositories {
    mavenCentral()
}
dependencies {
    compile 'com.github.mkopylec:errorest-spring-boot-starter:1.0.0'
}
```

## HTTP response format
The starter maps all unhandled exceptions to specific HTTP response.

## Basic usage
To start using the starter in a REST service teh service must be a Spring Boot web application:
```java
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```
No extra configuration is needed to ensure that all thrown exceptions will be properly mapped to HTTP responses.

## Advanced usage
The starter supports the following features to make error handling as easy as possible.

### 

## License
ErroREST Spring Boot Starter is published under [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0).
