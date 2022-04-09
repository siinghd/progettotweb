package com.progetto.progettotweb.routes.auth.admin;

import com.progetto.progettotweb.controllers.RipetizioneController;
import com.progetto.progettotweb.models.Ripetizione;
import com.progetto.progettotweb.models.Utente;
import com.progetto.progettotweb.utils.ResponseToJson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "prenotazionieffettuateall", value = "/api/auth/admin/prenotazioniall")
public class RoutePrenotazioniAll extends HttpServlet {

    private final RipetizioneController ripetizioneController = new RipetizioneController();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int limit = 10;
        int currentPage = 0;
        PrintWriter writer = response.getWriter();
        HttpSession s = request.getSession();
        try {
            limit = Integer.parseInt(request.getParameter("limit"));
            currentPage = Integer.parseInt(request.getParameter("currentpage"));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        ArrayList<Ripetizione> ripetizioni = ripetizioneController.queryDBRipetizioniAll(limit,currentPage);
        response.setStatus(HttpServletResponse.SC_OK);
        writer.write(ResponseToJson.toJsonMessage("success","Ricerca ripetizioni prenotate", ripetizioni));
        writer.close();
    }

}
