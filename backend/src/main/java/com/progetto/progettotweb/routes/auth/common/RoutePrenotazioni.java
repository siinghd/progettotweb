package com.progetto.progettotweb.routes.auth.common;
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

@WebServlet(name = "prenotazionidisponibili", value = "/api/auth/common/prenotazionidisponibili")
public class RoutePrenotazioni  extends HttpServlet{

    private final RipetizioneController ripetizioneController = new RipetizioneController();


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException{

        int idCorso = -1;
        int idDocente = -1;
        int idUtente = -1;
        int status = -1;
        String data = "";
        String ora = "";
        PrintWriter printWriter = response.getWriter();
        HttpSession s = request.getSession();
        Utente u = (Utente)s.getAttribute("user");
        idUtente = u.getId();

        try {
            idCorso = Integer.parseInt(request.getParameter("idcorso"));
            idDocente = Integer.parseInt(request.getParameter("iddocente"));
            status = Integer.parseInt(request.getParameter("status"));
            data = (String)(request.getParameter("data"));
            ora = (String)(request.getParameter("ora"));

        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            printWriter.write(ResponseToJson.toJsonMessage("fail", "Errore nell'inserimento della prenotazione"));
            printWriter.close();
            return;
        }
        if (idCorso ==-1  || idDocente == -1 || idUtente == -1 || status == -1 || data.equals("") || ora.equals("")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            printWriter.write(ResponseToJson.toJsonMessage("fail","Controlla che tutti i campi siano presenti"));
            printWriter.close();
            return;
        }
        ArrayList<Ripetizione> ripetizioniPerLaStessData = ripetizioneController.queryDBRipetizioniByIdUserAndIdDocDateTime(idUtente,idDocente,data,ora);
        if(ripetizioniPerLaStessData.size()>0){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            printWriter.write(ResponseToJson.toJsonMessage("fail","La prenotazione non puo essere effettuta per la data e ora selezionata," +
                    "hai gia prenotato per questa data o il docente e gia stato prenotato!"));
            return;
        }
        Ripetizione ripetizione = new Ripetizione(idDocente, idCorso, idUtente, data, ora, status );
        String value = ripetizioneController.insertRipetizione(ripetizione);

        if(value.equals("ok")){
            response.setStatus(HttpServletResponse.SC_OK);
            printWriter.write(ResponseToJson.toJsonMessage("success","Prenotaione effettuata con successo"));
        }else{
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            printWriter.write(ResponseToJson.toJsonMessage("fail","Problema nel creare il corso associato al docente",value));
        }
        printWriter.close();

    }
}
