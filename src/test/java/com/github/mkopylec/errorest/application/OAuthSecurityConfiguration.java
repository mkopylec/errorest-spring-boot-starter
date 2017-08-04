package com.github.mkopylec.errorest.application;

import com.github.mkopylec.errorest.handling.errordata.security.oauth.ErrorestOAuthAccessDeniedHandler;
import com.github.mkopylec.errorest.handling.errordata.security.oauth.ErrorestOAuthAuthenticationEntryPoint;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableWebSecurity
@EnableResourceServer
@Profile("security-oauth")
public class OAuthSecurityConfiguration extends ResourceServerConfigurerAdapter {

    private final ErrorestOAuthAuthenticationEntryPoint authenticationEntryPoint;
    private final ErrorestOAuthAccessDeniedHandler accessDeniedHandler;

    public OAuthSecurityConfiguration(ErrorestOAuthAuthenticationEntryPoint authenticationEntryPoint, ErrorestOAuthAccessDeniedHandler accessDeniedHandler) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/controller/oauth-authentication-error").authenticated();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.authenticationEntryPoint(authenticationEntryPoint).accessDeniedHandler(accessDeniedHandler);
    }
}
