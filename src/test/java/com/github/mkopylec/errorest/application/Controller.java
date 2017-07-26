package com.github.mkopylec.errorest.application;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

@RestController
@RequestMapping("/controller")
public class Controller {

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

    @GetMapping(path = "/type-mismatch", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public void throwTypeMismatchException(@RequestParam("query-parameter") int parameter) {
    }

    @GetMapping(path = "/no-error", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public void throwNoError() {
    }
}
