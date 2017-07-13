package com.github.mkopylec.errorest.application;

import com.github.mkopylec.errorest.exceptions.RestApplicationException;
import com.github.mkopylec.errorest.logging.LoggingLevel;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Component
public class ServletFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        throw new RestApplicationException("code", "desc", HttpStatus.BAD_REQUEST, LoggingLevel.WARN, false) {};
    }
}
