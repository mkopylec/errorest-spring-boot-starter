package com.github.mkopylec.errorest.application;

import com.github.mkopylec.errorest.handling.errordata.security.ErrorestAuthenticationEntryPoint;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling().authenticationEntryPoint(new ErrorestAuthenticationEntryPoint())
                .and().authorizeRequests()
                .antMatchers("/controller/secured-with-configuration").authenticated();
    }
}
