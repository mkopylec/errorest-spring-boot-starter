package com.github.mkopylec.errorest.configuration;

import com.github.mkopylec.errorest.handling.errordata.ErrorDataProviderContext;
import com.github.mkopylec.errorest.handling.errordata.SecurityErrorDataProviderContext;
import com.github.mkopylec.errorest.handling.errordata.security.ErrorestAccessDeniedHandler;
import com.github.mkopylec.errorest.handling.errordata.security.ErrorestAuthenticationEntryPoint;
import com.github.mkopylec.errorest.handling.errordata.security.ErrorsHttpResponseSetter;
import com.github.mkopylec.errorest.response.ErrorsFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;

import java.nio.file.AccessDeniedException;

@Configuration
@ConditionalOnClass({AccessDeniedException.class, AuthenticationException.class})
@EnableConfigurationProperties(ErrorestProperties.class)
public class SecurityErrorestConfiguration {

    protected final ErrorestProperties errorestProperties;

    public SecurityErrorestConfiguration(ErrorestProperties errorestProperties) {
        this.errorestProperties = errorestProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public ErrorDataProviderContext errorDataProviderContext() {
        return new SecurityErrorDataProviderContext(errorestProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public ErrorestAccessDeniedHandler errorestAccessDeniedHandler(ErrorDataProviderContext providerContext, ErrorsFactory errorsFactory, ErrorsHttpResponseSetter responseSetter) {
        return new ErrorestAccessDeniedHandler(providerContext, errorsFactory, responseSetter);
    }

    @Bean
    @ConditionalOnMissingBean
    public ErrorestAuthenticationEntryPoint errorestAuthenticationEntryPoint(ErrorDataProviderContext providerContext, ErrorsFactory errorsFactory, ErrorsHttpResponseSetter responseSetter) {
        return new ErrorestAuthenticationEntryPoint(providerContext, errorsFactory, responseSetter);
    }

    @Bean
    @ConditionalOnMissingBean
    public ErrorsHttpResponseSetter errorsHttpResponseSetter() {
        return new ErrorsHttpResponseSetter();
    }
}
