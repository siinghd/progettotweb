package com.progetto.progettotweb.routes.auth.common;
import com.google.gson.Gson;
import com.progetto.progettotweb.controllers.CorsoController;
import com.progetto.progettotweb.models.Corso;
import com.progetto.progettotweb.utils.ResponseToJson;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "getsubject", value = "/api/auth/common/getsubjects")
public class RouteGetSubjects extends HttpServlet {
    private final CorsoController corsoController= new CorsoController();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter printWriter = response.getWriter();
        ArrayList<Corso> subjects = corsoController.queryDBCorso();
        response.setStatus(HttpServletResponse.SC_OK);
        printWriter.write(ResponseToJson.toJsonMessage("success","Ricerca Corsi",subjects));
        printWriter.close();
    }
}
