package com.github.mkopylec.errorest.application;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.filter.OncePerRequestFilter;
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
