package com.github.mkopylec.errorest.configuration;

import com.github.mkopylec.errorest.handling.ControllerErrorHandler;
import com.github.mkopylec.errorest.handling.RequestMethodAttributeSettingFilter;
import com.github.mkopylec.errorest.handling.ServletFilterErrorHandler;
import com.github.mkopylec.errorest.handling.providers.ErrorDataProviderContext;
import com.github.mkopylec.errorest.logging.ExceptionLogger;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureBefore(ErrorMvcAutoConfiguration.class)
@EnableConfigurationProperties({ErrorestProperties.class, ServerProperties.class})
public class ErrorestConfiguration {

    protected final ErrorestProperties errorestProperties;
    protected final ServerProperties serverProperties;

    public ErrorestConfiguration(ErrorestProperties errorestProperties, ServerProperties serverProperties) {
        this.errorestProperties = errorestProperties;
        this.serverProperties = serverProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public ControllerErrorHandler controllerErrorHandler(ExceptionLogger exceptionLogger) {
        return new ControllerErrorHandler(exceptionLogger);
    }

    @Bean
    @ConditionalOnMissingBean
    public ServletFilterErrorHandler servletFilterErrorHandler(ErrorAttributes errorAttributes, ExceptionLogger logger, ErrorDataProviderContext providerContext) {
        return new ServletFilterErrorHandler(errorAttributes, serverProperties, errorestProperties, logger, providerContext);
    }

    @Bean
    @ConditionalOnMissingBean
    public RequestMethodAttributeSettingFilter requestMethodAttributeSettingFilter() {
        return new RequestMethodAttributeSettingFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    public ExceptionLogger exceptionLogger() {
        return new ExceptionLogger();
    }

    @Bean
    @ConditionalOnMissingBean
    public ErrorDataProviderContext errorDataProviderContext() {
        return new ErrorDataProviderContext(errorestProperties);
    }
}
