package com.progetto.progettotweb.routes;
import com.google.gson.Gson;
import com.progetto.progettotweb.controllers.RipetizioneController;
import com.progetto.progettotweb.models.Docente;
import com.progetto.progettotweb.models.Ripetizione;
import com.progetto.progettotweb.models.Utente;
import com.progetto.progettotweb.utils.ResponseToJson;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "getprenotazionidisponibili", value = "/api/getprenotazionidisponibili")
public class RouteGetsBookingsDisponibili  extends HttpServlet{

    private final RipetizioneController ripetizioneController = new RipetizioneController();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
        PrintWriter writer = response.getWriter();
        ArrayList<Ripetizione> ripetizioni = ripetizioneController.queryDBRipetizioniDisponibili();
        response.setStatus(HttpServletResponse.SC_OK);
        writer.write(ResponseToJson.toJsonMessage("success","Ricerca ripetizioni disponibili", ripetizioni));
        writer.close();
    }

}
