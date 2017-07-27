package com.github.mkopylec.errorest.handling;

import org.springframework.core.Ordered;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.github.mkopylec.errorest.handling.utils.HttpUtils.getHeadersAsText;

public class RequestAttributeSettingFilter extends OncePerRequestFilter implements Ordered {

    public static final String REQUEST_METHOD_ERROR_ATTRIBUTE = RequestAttributeSettingFilter.class.getName() + ".method";
    public static final String REQUEST_HEADERS_ERROR_ATTRIBUTE = RequestAttributeSettingFilter.class.getName() + ".headers";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (Throwable ex) {
            request.setAttribute(REQUEST_METHOD_ERROR_ATTRIBUTE, request.getMethod());
            request.setAttribute(REQUEST_HEADERS_ERROR_ATTRIBUTE, getHeadersAsText(request));
            throw ex;
        }
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }
}
