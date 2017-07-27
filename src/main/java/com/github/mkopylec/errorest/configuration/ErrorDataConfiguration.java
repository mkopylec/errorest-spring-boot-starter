package com.github.mkopylec.errorest.configuration;

import com.github.mkopylec.errorest.handling.errordata.ErrorDataProviderContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnMissingClass("org.springframework.security.access.AccessDeniedException")
@EnableConfigurationProperties(ErrorestProperties.class)
public class ErrorDataConfiguration {

    protected final ErrorestProperties errorestProperties;

    public ErrorDataConfiguration(ErrorestProperties errorestProperties) {
        this.errorestProperties = errorestProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public ErrorDataProviderContext errorDataProviderContext() {
        return new ErrorDataProviderContext(errorestProperties);
    }
}
