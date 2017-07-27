package com.github.mkopylec.errorest.configuration;

import com.github.mkopylec.errorest.handling.ControllerErrorHandler;
import com.github.mkopylec.errorest.handling.RequestAttributeSettingFilter;
import com.github.mkopylec.errorest.handling.ServletFilterErrorHandler;
import com.github.mkopylec.errorest.handling.errordata.ErrorDataProviderContext;
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
    public ControllerErrorHandler controllerErrorHandler(ExceptionLogger exceptionLogger, ErrorDataProviderContext providerContext) {
        return new ControllerErrorHandler(errorestProperties, exceptionLogger, providerContext);
    }

    @Bean
    @ConditionalOnMissingBean
    public ServletFilterErrorHandler servletFilterErrorHandler(ErrorAttributes errorAttributes, ExceptionLogger logger, ErrorDataProviderContext providerContext) {
        return new ServletFilterErrorHandler(errorAttributes, serverProperties, errorestProperties, logger, providerContext);
    }

    @Bean
    @ConditionalOnMissingBean
    public RequestAttributeSettingFilter requestMethodAttributeSettingFilter() {
        return new RequestAttributeSettingFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    public ExceptionLogger exceptionLogger() {
        return new ExceptionLogger();
    }
}
