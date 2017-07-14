package com.github.mkopylec.errorest.application;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import static org.springframework.boot.SpringApplication.run;

@ServletComponentScan
@SpringBootApplication
public class RestApplication {

    public static void main(String[] args) {
        run(RestApplication.class, args);
    }
}
