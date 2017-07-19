package com.github.mkopylec.errorest.application;

import org.springframework.http.MediaType;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Arrays.asList;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_XML;

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
        if (uri.endsWith("/no-error")) {
            prepareResponse(request, response);
        }
    }

    private void prepareResponse(HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(200);
        response.setHeader(CONTENT_TYPE, request.getHeader(ACCEPT));
    }
}
