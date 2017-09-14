package com.github.mkopylec.errorest.handling.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class HttpUtils {

    public static String getHeadersAsText(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> names = request.getHeaderNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            if (!name.equals(AUTHORIZATION)) {
                headers.put(name, request.getHeader(name));
            }
        }
        return headers.toString();
    }
}
