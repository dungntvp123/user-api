package com.microservice.user.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component
public class ApiAuthorizationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uri = request.getRequestURI();

        if (uri.startsWith("/swagger-ui") || uri.startsWith("/v3/api-docs") || uri.startsWith("/swagger-ui.html")) {
            filterChain.doFilter(request, response);
            return;
        }

        String apiKey = request.getHeader("ApiKey");
        if (apiKey == null || !availableApiKey(apiKey)) {
            Map<String, String> body = new HashMap<>();
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            body.put("message", "ApiKey is invalid or missing");
            body.put("status", "error");
            String jsonBody = (new ObjectMapper()).writeValueAsString(body);
            writer.write(jsonBody);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private boolean availableApiKey(String authorization) {
        return true;
    }
}
