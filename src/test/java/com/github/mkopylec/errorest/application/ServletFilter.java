package com.github.mkopylec.errorest.application;

import com.github.mkopylec.errorest.client.ErrorestTemplate;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_XML;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.http.MediaType.TEXT_PLAIN;

@WebFilter("/filter/*")
public class ServletFilter extends OncePerRequestFilter {

    private final RestOperations rest = new ErrorestTemplate();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        if (uri.endsWith("/exception")) {
            throw new RuntimeException("Exception from servlet filer");
        }
        if (uri.endsWith("/media-type-not-acceptable")) {
            throw new HttpMediaTypeNotAcceptableException(asList(APPLICATION_JSON, APPLICATION_XML));
        }
        if (uri.endsWith("/media-type-not-supported")) {
            throw new HttpMediaTypeNotSupportedException(TEXT_HTML, singletonList(TEXT_PLAIN));
        }
        if (uri.endsWith("/message-not-readable")) {
            throw new HttpMessageNotReadableException("Message not readable from servlet filter");
        }
        if (uri.endsWith("/missing-servlet-request-parameter")) {
            throw new MissingServletRequestParameterException("query-parameter", "String");
        }
        if (uri.endsWith("/missing-servlet-request-part")) {
            throw new MissingServletRequestPartException("part");
        }
        if (uri.endsWith("/no-handler-found")) {
            throw new NoHandlerFoundException(request.getMethod(), request.getRequestURI(), null);
        }
        if (uri.endsWith("/request-method-not-supported")) {
            throw new HttpRequestMethodNotSupportedException(request.getMethod(), singletonList("DELETE"));
        }
        if (uri.endsWith("/servlet-request-binding")) {
            throw new ServletRequestBindingException("Exception from servlet filter");
        }
        if (uri.endsWith("/type-mismatch")) {
            String parameter = request.getParameter("query-parameter");
            NumberFormatException ex = new NumberFormatException("For input string: \"" + parameter + "\"");
            throw new MethodArgumentTypeMismatchException(parameter, int.class, null, null, ex);
        }
        if (uri.endsWith("/application")) {
            throw new TestApplicationException();
        }
        if (uri.endsWith("/external-request")) {
            rest.getForObject("http://localhost:10000/external/resource", String.class);
        }
        if (uri.endsWith("/no-error")) {
            prepareResponse(request, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private void prepareResponse(HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(200);
        response.setHeader(CONTENT_TYPE, request.getHeader(ACCEPT));
    }
}
