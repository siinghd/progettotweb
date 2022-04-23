package com.progetto.progettotweb.routes;
import com.google.gson.Gson;
import com.progetto.progettotweb.controllers.DAO;
import com.progetto.progettotweb.controllers.UtenteController;
import com.progetto.progettotweb.models.Utente;
import com.progetto.progettotweb.utils.BCrypt;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "signout", value = "/api/signout")
public class Signout extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter printWriter = response.getWriter();
        response.setContentType("application/json");
        request.getSession().invalidate();
        response.setStatus(HttpServletResponse.SC_OK);
        printWriter.write("{\"status\":\"success\", \"message\":\"Signout avvenuto con successo.\"}");
        printWriter.close();
    }
}
