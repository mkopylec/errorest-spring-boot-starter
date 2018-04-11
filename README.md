# ErroREST Spring Boot Starter
[![Build Status](https://travis-ci.org/mkopylec/errorest-spring-boot-starter.svg?branch=master)](https://travis-ci.org/mkopylec/errorest-spring-boot-starter)
[![Coverage Status](https://coveralls.io/repos/mkopylec/errorest-spring-boot-starter/badge.svg?branch=master&service=github)](https://coveralls.io/github/mkopylec/errorest-spring-boot-starter?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.mkopylec/errorest-spring-boot-starter/badge.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/com.github.mkopylec/errorest-spring-boot-starter)

Complete, ready to use exception handling for Spring Boot REST services.

## Features
- unified HTTP response format for **every** unhandled exception (thrown from controllers or servlet filters)
- unified exception log format
- JSON and XML HTTP response body types
- configurable HTTP response body format
- support for custom exceptions
- support for [Bean Validation](http://beanvalidation.org/) exceptions
- support for [Spring Web](https://docs.spring.io/spring/docs/current/spring-framework-reference/html/spring-web.html) 4xx errors
- [RestTemplate](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html) that supports ErroREST HTTP responses
- support for [Spring Security](https://projects.spring.io/spring-security/) exceptions

## Installing
```gradle
repositories {
    mavenCentral()
}
dependencies {
    compile 'com.github.mkopylec:errorest-spring-boot-starter:2.0.0'
}
```

## HTTP response format
The starter maps all unhandled exceptions to specific ErroREST HTTP responses.

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

## Log format
All unhandled exceptions are logged using the following template:
```
ID: <random id> | <http request method> <http request uri> <http response status> | <some error code>: <some error description> | <another error code>: <another error description> | ...
```
Exceptions are always logged with stack trace.

## Basic usage
To start using the starter in a REST service the service must be a Spring Boot web application:
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
errorest.response-body-format: without_descriptions
```

### Custom exceptions
Custom exceptions can be configured how they will be mapped to ErroREST HTTP responses by extending `ApplicationException`, for example:
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
The `SampleApplicationException` from the example will be mapped to ErroREST HTTP response with 400 (bad request) status and body like so:
```json
{
    "id":"<random id>",
    "errors":[
        {
            "code":"SAMPLE_ERROR_CODE",
            "description":message
        }
    ]
}
```
The exception will be logged as warning.

### Bean Validation exceptions
The starter is able to map [Bean Validation](http://beanvalidation.org/) exceptions to ErroREST HTTP responses with 422 statuses (unprocessable entity).
For this kind of exceptions a validation constraint `message` is mapped to an error code and an error description becomes a description of the invalid value.
For example, if the following validation fails:
```java
public class SampleRequestBody {

    @NotNull(message = "EMPTY_MESSAGE")
    private String message;
    @Max(message = "NUMBER_TOO_BIG", value = 10)
    private int number;

    ...
}
```
the result will mapped to ErroREST HTTP response with body:
```json
{
    "id":"<random id>",
    "errors":[
        {
            "code":"EMPTY_MESSAGE",
            "description":"Invalid 'message' value: null"
        },        
        {
            "code":"NUMBER_TOO_BIG",
            "description":"Invalid 'number' value: 11"
        }
    ]
}
```
By default all validation errors are logged with `WARN` level.
To change the logging level set an appropriate configuration property:
```yaml
errorest.bean-validation-error.logging-level: <logging level>
```

### HTTP 4xx errors
[Spring Web](https://docs.spring.io/spring/docs/current/spring-framework-reference/html/spring-web.html) includes a set of generic exceptions that represents HTTP client errors,
for example [`HttpMessageNotReadableException`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/converter/HttpMessageNotReadableException.html) or [`HttpRequestMethodNotSupportedException`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/HttpRequestMethodNotSupportedException.html).
Those exceptions are automatically mapped to ErroREST HTTP responses with a proper HTTP statuses.
By default 4xx HTTP errors are logged with `WARN` level.
To change the logging level set an appropriate configuration property:
```yaml
errorest.http-client-error.logging-level: <logging level>
```

### RestTemplate
The starter includes a special [`RestTemplate`](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html) called `ErrorestTemplate` which fully supports ErroREST HTTP responses.
The `ErrorestTemplate` in case of HTTP error throws `ExternalHttpRequestException` which extends [`HttpStatusCodeException`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/HttpStatusCodeException.html).
The `ExternalHttpRequestException` overrides the `getMessage()` method to return much more detailed information about HTTP error than [`HttpStatusCodeException`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/HttpStatusCodeException.html).
It also defines `getResponseBodyAsErrors()` method which maps ErroREST HTTP response bodies to `Errors` objects.
The usage of `ErrorestTemplate` is the same as of normal [`RestTemplate`](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html):
```java
    RestOperations client = new ErrorestTemplate();
    try {
        client.getForObject("http://exmaple.com", String.class);
    } catch (ExternalHttpRequestException ex) {
        String detailedMessage = ex.getMessage();
        Errors errors = ex.getResponseBodyAsErrors();
        ...
    }
```
If the `ExternalHttpRequestException` will not be handled manually it will be handled automatically by the starter.
In that case a received ErroREST HTTP response will be proxied up to the client.

### Spring Security exceptions
The starter supports mapping [Spring Security](https://projects.spring.io/spring-security/) exceptions to ErroREST HTTP responses if the following dependency is added:
```gradle
dependencies {
    compile 'org.springframework.boot:spring-boot-starter-security:1.5.6.RELEASE'
}
```
To support the [HTTP Security configuration](https://docs.spring.io/spring-security/site/docs/current/reference/html/jc.html#jc-httpsecurity) and [method secured with annotations](https://docs.spring.io/spring-security/site/docs/current/reference/html/jc.html#jc-method) `ErrorestAccessDeniedHandler` and `ErrorestAuthenticationEntryPoint` are needed:
```java
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private ErrorestAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private ErrorestAccessDeniedHandler accessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                ...
    }
}
```

## Configuration properties list
The following list contains all available configuration properties with their default values.
```yaml
errorest:
  response-body-format: full # HTTP response body format.
  http-client-error:
    logging-level: warn # HTTP 4xx errors logging level.
  bean-validation-error:
    logging-level: warn # Validation errors logging level.
```

## License
ErroREST Spring Boot Starter is published under [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0).
