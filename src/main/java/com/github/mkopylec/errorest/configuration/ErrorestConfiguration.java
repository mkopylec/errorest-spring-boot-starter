package com.github.mkopylec.errorest.configuration;

import com.github.mkopylec.errorest.handlers.RequestMethodAttributeSettingFilter;
import com.github.mkopylec.errorest.handlers.ServletFilterErrorHandler;
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
@EnableConfigurationProperties(ServerProperties.class)
public class ErrorestConfiguration {

//    @Bean
//    @ConditionalOnMissingBean
//    public ControllerErrorHandler controllerErrorHandler() {
//        return new ControllerErrorHandler();
//    }

    @Bean
    @ConditionalOnMissingBean
    public ServletFilterErrorHandler servletFilterErrorHandler(ErrorAttributes errorAttributes, ServerProperties serverProperties) {
        return new ServletFilterErrorHandler(errorAttributes, serverProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public RequestMethodAttributeSettingFilter requestMethodAttributeSettingFilter() {
        return new RequestMethodAttributeSettingFilter();
    }
}
