# ErroREST Spring Boot Starter
[![Build Status](https://travis-ci.org/mkopylec/errorest-spring-boot-starter.svg?branch=master)](https://travis-ci.org/mkopylec/errorest-spring-boot-starter)
[![Coverage Status](https://coveralls.io/repos/mkopylec/errorest-spring-boot-starter/badge.svg?branch=master&service=github)](https://coveralls.io/github/mkopylec/errorest-spring-boot-starter?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.mkopylec/errorest-spring-boot-starter/badge.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/com.github.mkopylec/errorest-spring-boot-starter)

Complete, ready to use exception handling for Spring Boot REST services.

## Features
- unified HTTP response format for **every** unhandled exception (thrown from controllers or servlet filters)
- JSON and XML HTTP response body types
- configurable HTTP response body format
- support for creating custom exceptions
- support for handling external HTTP requests errors
- support for [Spring Security](https://projects.spring.io/spring-security/) exceptions

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
The starter maps all unhandled exceptions to specific ErroREST HTTP response.

### JSON format
If the incoming HTTP request contains `Accept: application/json` header, the HTTP response will contain `Content-Type: application/json` header.
The response body will be:
```json
{
    "id":"<random id>",
    "errors":[
        {
            "code":"<some error code>",
            "description":"<some error description>"
        }
    ]
}
```

### XML format
If the incoming HTTP request contains `Accept: application/xml` header, the HTTP response will contain `Content-Type: application/xml` header.
The response body will be:
```xml
<body>
    <id>random id</id>
    <errors>
        <error>
            <code>some error code</code>
            <description>some error description</description>
        </error>
    </errors>
</body>
```

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
No extra configuration and no custom exception handler is needed to ensure that all thrown exceptions will be properly mapped to ErroREST HTTP responses.

## Advanced usage
The starter supports the following features to make error handling as easy as possible.

### HTTP response body type
By default response body includes error codes and descriptions.
To hide error descriptions set an appropriate configuration property:
```yaml
errorest.response-body-format: WITHOUT_DESCRIPTIONS
```

### Custom exceptions
Custom exceptions will be automatically mapped to ErroREST HTTP response if they extend `ApplicationException`, for example:
```java
public class SampleApplicationException extends ApplicationException {

    public SampleApplicationException(String message, Throwable cause) {
        super(new ApplicationExceptionConfiguration()
                .withResponseHttpStatus(BAD_REQUEST)
                .withLoggingLevel(WARN)
                .withCause(cause)
                .addError("SAMPLE_ERROR_CODE", message)
        );
    }
}
```

### External HTTP request errors
The starter includes a special [`RestTemplate`](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html) called `ErrorestTemplate` which fully supports ErroREST HTTP responses.
The `ErrorestTemplate` in case of HTTP error throws `ExternalHttpRequestException` which extends [`HttpStatusCodeException`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/HttpStatusCodeException.html).
The `ExternalHttpRequestException` overrides the `getMessage()` method to return much more detailed information about HTTP error than `HttpStatusCodeException`.
It also defines `getResponseBodyAsErrors()` method which maps ErroREST HTTP response bodies to `Errors` objects.

## License
ErroREST Spring Boot Starter is published under [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0).
