package com.github.mkopylec.errorest.application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestService {

    @GetMapping("/rest/error")
    public void fail() {
//        throw new RestException("code", "desc", HttpStatus.BAD_REQUEST, LoggingLevel.WARN, true) {};
        throw new RuntimeException("A controller error has occurred");
    }
}
