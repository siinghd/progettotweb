package com.progetto.progettotweb.routes.auth;

import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter("/api/auth/*")
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        String loginURI = request.getContextPath() + "/api/login";
        boolean loggedIn = session != null && session.getAttribute("user") != null;
        boolean loginRequest = request.getRequestURI().equals(loginURI);
        PrintWriter printWriter = response.getWriter();
        if (loggedIn || loginRequest) {
            filterChain.doFilter(request, response);
        } else {
            printWriter.write("{\"status\":\"fail\", \"message\":\"Utente non ha eseguito il login\",\"error\":\"LOGINREQUIRED\"}");
            printWriter.close();
        }
    }
}