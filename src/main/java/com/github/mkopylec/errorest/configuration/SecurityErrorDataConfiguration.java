package com.github.mkopylec.errorest.configuration;

import com.github.mkopylec.errorest.handling.errordata.ErrorDataProviderContext;
import com.github.mkopylec.errorest.handling.errordata.SecurityErrorDataProviderContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.AccessDeniedException;

@Configuration
@ConditionalOnClass(AccessDeniedException.class)
@EnableConfigurationProperties(ErrorestProperties.class)
public class SecurityErrorDataConfiguration {

    protected final ErrorestProperties errorestProperties;

    public SecurityErrorDataConfiguration(ErrorestProperties errorestProperties) {
        this.errorestProperties = errorestProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public ErrorDataProviderContext errorDataProviderContext() {
        return new SecurityErrorDataProviderContext(errorestProperties);
    }
}
