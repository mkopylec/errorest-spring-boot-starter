package com.github.mkopylec.errorest.application;

import com.github.mkopylec.errorest.handling.errordata.security.ErrorestAccessDeniedHandler;
import com.github.mkopylec.errorest.handling.errordata.security.ErrorestAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final ErrorestAuthenticationEntryPoint authenticationEntryPoint;
    private final ErrorestAccessDeniedHandler accessDeniedHandler;

    public SecurityConfiguration(ErrorestAuthenticationEntryPoint authenticationEntryPoint, ErrorestAccessDeniedHandler accessDeniedHandler) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().requireCsrfProtectionMatcher(new AntPathRequestMatcher("/controller/access-denied-via-configuration"))
                .and()
                .httpBasic()
                .and()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).accessDeniedHandler(accessDeniedHandler)
                .and().authorizeRequests()
                .antMatchers("/controller/authentication-error-via-configuration").authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("password").roles("role");
    }

    @SuppressWarnings("deprecation")
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
