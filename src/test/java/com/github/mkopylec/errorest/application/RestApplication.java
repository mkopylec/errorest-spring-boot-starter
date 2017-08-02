package com.github.mkopylec.errorest.application;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

import static org.springframework.boot.SpringApplication.run;

@ServletComponentScan
@SpringBootApplication
public class RestApplication {

    public static void main(String[] args) {
        run(RestApplication.class, args);
    }

    @Bean
    public EmbeddedServletContainerFactory tomcat() {
        return new TomcatEmbeddedServletContainerFactory();
    }
}
