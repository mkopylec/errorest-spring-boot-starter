package com.github.mkopylec.errorest.application;

import com.github.mkopylec.errorest.exceptions.RestException;
import com.github.mkopylec.errorest.logging.LoggingLevel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ServletFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        throw new RestException("code", "desc", HttpStatus.BAD_REQUEST, LoggingLevel.WARN, false) {};
    }
}
