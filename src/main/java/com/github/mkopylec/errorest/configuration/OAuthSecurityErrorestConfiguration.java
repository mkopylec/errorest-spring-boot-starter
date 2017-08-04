package com.github.mkopylec.errorest.configuration;

import com.github.mkopylec.errorest.handling.errordata.ErrorDataProviderContext;
import com.github.mkopylec.errorest.handling.errordata.OAuthSecurityErrorDataProviderContext;
import com.github.mkopylec.errorest.handling.errordata.security.oauth.ErrorestOAuthAccessDeniedHandler;
import com.github.mkopylec.errorest.handling.errordata.security.oauth.ErrorestOAuthAuthenticationEntryPoint;
import com.github.mkopylec.errorest.handling.errordata.security.oauth.OAuthExceptionRenderer;
import com.github.mkopylec.errorest.response.ErrorsFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import java.nio.file.AccessDeniedException;

@Configuration
@ConditionalOnClass({AccessDeniedException.class, AuthenticationException.class, OAuth2Exception.class})
@EnableConfigurationProperties(ErrorestProperties.class)
public class OAuthSecurityErrorestConfiguration {

    protected final ErrorestProperties errorestProperties;

    public OAuthSecurityErrorestConfiguration(ErrorestProperties errorestProperties) {
        this.errorestProperties = errorestProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public ErrorDataProviderContext errorDataProviderContext() {
        return new OAuthSecurityErrorDataProviderContext(errorestProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public ErrorestOAuthAccessDeniedHandler errorestOAuthAccessDeniedHandler(ErrorDataProviderContext providerContext, ErrorsFactory errorsFactory) {
        OAuthExceptionRenderer exceptionRenderer = new OAuthExceptionRenderer(providerContext, errorsFactory);
        return new ErrorestOAuthAccessDeniedHandler(exceptionRenderer);
    }

    @Bean
    @ConditionalOnMissingBean
    public ErrorestOAuthAuthenticationEntryPoint errorestOAuthAuthenticationEntryPoint(ErrorDataProviderContext providerContext, ErrorsFactory errorsFactory) {
        OAuthExceptionRenderer exceptionRenderer = new OAuthExceptionRenderer(providerContext, errorsFactory);
        return new ErrorestOAuthAuthenticationEntryPoint(exceptionRenderer);
    }
}
