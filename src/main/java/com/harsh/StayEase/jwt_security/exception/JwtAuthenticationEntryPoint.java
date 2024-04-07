package com.harsh.StayEase.jwt_security.exception;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.security.core.AuthenticationException;

import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Custom authentication entry point for handling unauthorized requests.
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * Handles unauthorized requests by sending a 401 Unauthorized response.
     * 
     * @param request The HTTP servlet request.
     * @param response The HTTP servlet response.
     * @param authException The authentication exception that caused the unauthorized request.
     * @throws IOException If an I/O error occurs while handling the request or response.
     * @throws ServletException If a servlet-specific error occurs while handling the request or response.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = response.getWriter();
        writer.println("Access Denied!!!" + authException.getMessage());
    }    
}