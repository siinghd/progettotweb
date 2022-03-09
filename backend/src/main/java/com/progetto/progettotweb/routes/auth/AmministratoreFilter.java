package com.progetto.progettotweb.routes.auth;

import com.progetto.progettotweb.models.Utente;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter("/api/auth/admin/*")
public class AmministratoreFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        Utente user = (Utente) session.getAttribute("user");
        PrintWriter printWriter = response.getWriter();
        response.setContentType("application/json");
        if (user.getRuolo()==3) {
            filterChain.doFilter(request, response);
        } else {
            printWriter.write("{\"status\":\"fail\", \"message\":\"Accesso negato,devi essere amministratore\",\"error\":\"ACCESSDENIED\"}");
            printWriter.close();
        }
    }
}
