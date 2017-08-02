package com.github.mkopylec.errorest.application;

import com.github.mkopylec.errorest.client.ErrorestTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestOperations;

import javax.validation.Valid;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

@RestController
@RequestMapping("/controller")
public class Controller {

    private final RestOperations rest = new ErrorestTemplate();

    @GetMapping(path = "/exception", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public void throwException() throws Exception {
        throw new Exception("Exception from controller");
    }

    @GetMapping(path = "/media-type-not-acceptable", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public void throwHttpMediaTypeNotAcceptableException() {
    }

    @GetMapping(path = "/media-type-not-supported", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE}, consumes = TEXT_PLAIN_VALUE)
    public void throwHttpMediaTypeNotSupportedException() {
    }

    @PostMapping(path = "/message-not-readable", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public void throwHttpMessageNotReadableException(@RequestBody Map<String, Boolean> body) {
    }

    @GetMapping(path = "/missing-servlet-request-parameter", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public void throwMissingServletRequestParameterException(@RequestParam("query-parameter") String parameter) {
    }

    @PostMapping(path = "/missing-servlet-request-part", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public void throwMissingServletRequestPartException(@RequestPart("part") Object part) {
    }

    @DeleteMapping(path = "/request-method-not-supported", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public void throwHttpRequestMethodNotSupportedException() {
    }

    @GetMapping(path = "/servlet-request-binding", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public void throwServletRequestBindingException(@RequestHeader("header") String header) {
    }

    @GetMapping(path = "/type-mismatch", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public void throwTypeMismatchException(@RequestParam("query-parameter") int parameter) {
    }

    @GetMapping(path = "/application", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public void throwApplicationException() {
        throw new TestApplicationException();
    }

    @GetMapping(path = "/external-request", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public void throwExternalHttpRequestException() {
        rest.getForObject("http://localhost:10000/external/resource", String.class);
    }

    @PostMapping(path = "/method-argument-not-valid", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public void throwMethodArgumentNotValidException(@RequestBody @Valid ValidatedRequest request) {
    }

    @GetMapping(path = "/access-denied-via-configuration", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public void throwAccessDeniedExceptionViaConfiguration() {
    }

    @PreAuthorize("authenticated")
    @GetMapping(path = "/access-denied-via-annotation", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public void throwAccessDeniedExceptionViaAnnotation() {
    }

    @GetMapping(path = "/authentication-error", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public void throwAuthenticationException() {
    }

    @GetMapping(path = "/no-error", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public void throwNoError() {
    }
}
