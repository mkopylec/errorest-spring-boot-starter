package com.github.mkopylec.errorest.handling.errordata.security.oauth;

import com.github.mkopylec.errorest.handling.errordata.ErrorData;
import com.github.mkopylec.errorest.handling.errordata.ErrorDataProvider;
import com.github.mkopylec.errorest.handling.errordata.ErrorDataProviderContext;
import com.github.mkopylec.errorest.handling.errordata.security.SecurityErrorDataProvider;
import com.github.mkopylec.errorest.response.Errors;
import com.github.mkopylec.errorest.response.ErrorsFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultOAuth2ExceptionRenderer;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.ResponseEntity.status;

public class OAuthExceptionRenderer extends DefaultOAuth2ExceptionRenderer {

    protected final ErrorDataProviderContext providerContext;
    protected final ErrorsFactory errorsFactory;

    public OAuthExceptionRenderer(ErrorDataProviderContext providerContext, ErrorsFactory errorsFactory) {
        this.providerContext = providerContext;
        this.errorsFactory = errorsFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void handleHttpEntityResponse(HttpEntity<?> responseEntity, ServletWebRequest webRequest) throws Exception {
        ResponseEntity<OAuth2Exception> response = (ResponseEntity<OAuth2Exception>) responseEntity;
        ErrorData errorData = getErrorData(response.getBody(), webRequest.getRequest());
        Errors errors = errorsFactory.logAndCreateErrors(errorData);
        ResponseEntity<Errors> errorsResponse = status(response.getStatusCode())
                .headers(response.getHeaders())
                .body(errors);
        super.handleHttpEntityResponse(errorsResponse, webRequest);
    }

    @SuppressWarnings("unchecked")
    protected ErrorData getErrorData(OAuth2Exception ex, HttpServletRequest request) {
        ErrorDataProvider provider = providerContext.getErrorDataProvider(ex);
        return ((SecurityErrorDataProvider) provider).createErrorData(ex, request);
    }
}
