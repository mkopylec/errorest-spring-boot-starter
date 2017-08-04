//package com.github.mkopylec.errorest.handling.errordata.security.oauth;
//
//import com.github.mkopylec.errorest.handling.errordata.ErrorData;
//import com.github.mkopylec.errorest.handling.errordata.ErrorDataProvider;
//import com.github.mkopylec.errorest.handling.errordata.ErrorDataProviderContext;
//import com.github.mkopylec.errorest.response.Errors;
//import com.github.mkopylec.errorest.response.ErrorsFactory;
//import com.github.mkopylec.errorest.response.OAuthErrors;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
//import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
//
//import javax.servlet.http.HttpServletRequest;
//
//import static com.github.mkopylec.errorest.handling.utils.HttpUtils.getCurrentRequest;
//import static org.springframework.http.ResponseEntity.status;
//
//public class OAuthErrorsTranslator extends DefaultWebResponseExceptionTranslator {
//
//    protected final ErrorDataProviderContext providerContext;
//    protected final ErrorsFactory errorsFactory;
//
//    public OAuthErrorsTranslator(ErrorDataProviderContext providerContext, ErrorsFactory errorsFactory) {
//        this.providerContext = providerContext;
//        this.errorsFactory = errorsFactory;
//    }
//
//    @Override
//    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
//        ResponseEntity<OAuth2Exception> response = super.translate(e);
//        ErrorData errorData = getErrorData(response.getBody(), getCurrentRequest());
//        Errors errors = errorsFactory.logAndCreateErrors(errorData);
//        OAuthErrors body = new OAuthErrors(errors);
//        return status(response.getStatusCode())
//                .headers(response.getHeaders())
//                .body(body);
//    }
//
//    @SuppressWarnings("unchecked")
//    protected ErrorData getErrorData(OAuth2Exception ex, HttpServletRequest request) {
//        ErrorDataProvider provider = providerContext.getErrorDataProvider(ex);
//        return provider.getErrorData(ex, request);
//    }
//}
