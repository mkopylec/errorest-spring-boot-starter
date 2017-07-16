package com.github.mkopylec.errorest.application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

@RestController
@RequestMapping("/controller")
public class Controller {

    @GetMapping(path = "/exception", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public void throwException() throws Exception {
        throw new Exception("Exception from controller");
    }

    @GetMapping(path = "/no-error", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public void throwNoError() {
    }
}
