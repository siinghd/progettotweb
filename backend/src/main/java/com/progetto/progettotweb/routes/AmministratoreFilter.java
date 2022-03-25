package com.progetto.progettotweb.routes;

import com.progetto.progettotweb.models.Utente;
import com.progetto.progettotweb.utils.ResponseToJson;

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
        PrintWriter printWriter = response.getWriter();
        if (session == null) {
            printWriter.write(ResponseToJson.toJsonMessage("fail","Session non valida", "Session expired"));
            printWriter.close();
            return;
        }
        Utente user = (Utente) session.getAttribute("user");
        if (user.getRuolo()==2) {
            filterChain.doFilter(request, response);
        } else {
            printWriter.write("{\"status\":\"fail\", \"message\":\"Accesso negato,devi essere amministratore\",\"error\":\"ACCESSDENIED\"}");
            printWriter.close();
        }
    }
}
