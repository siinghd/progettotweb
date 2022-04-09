package com.progetto.progettotweb.routes.auth.common;

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

@WebServlet(name = "prenotazionieffettuate", value = "/api/auth/common/prenotazionieffettuate")
public class RoutePrenotazioniEffettuate extends HttpServlet {

    private final RipetizioneController ripetizioneController = new RipetizioneController();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idUtente = -1;
        int limit = 10;
        int currentPage = 0;
        PrintWriter writer = response.getWriter();
        HttpSession s = request.getSession();
        Utente u = (Utente)s.getAttribute("user");
        idUtente = u.getId();
        try {
            limit = Integer.parseInt(request.getParameter("limit"));
            currentPage = Integer.parseInt(request.getParameter("currentpage"));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        ArrayList<Ripetizione> ripetizioni = ripetizioneController.queryDBRipetizioniPrenotate(idUtente,limit,currentPage);
        response.setStatus(HttpServletResponse.SC_OK);
        writer.write(ResponseToJson.toJsonMessage("success","Ricerca ripetizioni prenotate", ripetizioni));
        writer.close();
    }

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

        Ripetizione ripetizione = new Ripetizione(idDocente, idCorso, idUtente, data, ora, status );
        String value = ripetizioneController.insertRipetizione(ripetizione);

        if(value.equals("ok")){
            response.setStatus(HttpServletResponse.SC_OK);
            printWriter.write(ResponseToJson.toJsonMessage("success","Prenotazione effettuata con successo"));
        }else{
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            printWriter.write(ResponseToJson.toJsonMessage("fail","Problema nel creare la prenotazione",value));
        }
        printWriter.close();

    }
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException{

        int idRipetizione = -1;
        int idUtente = -1;
        int status = -1;
        PrintWriter printWriter = response.getWriter();
        HttpSession s = request.getSession();
        Utente u = (Utente)s.getAttribute("user");
        idUtente = u.getId();

        try {
            idRipetizione = Integer.parseInt(request.getParameter("idripetizione"));
            status = Integer.parseInt(request.getParameter("statuschange"));
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            printWriter.write(ResponseToJson.toJsonMessage("fail", "Errore nell'inserimento della prenotazione"));
            printWriter.close();
            return;
        }
        if (idRipetizione ==-1  || status == -1 || idUtente == -1) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            printWriter.write(ResponseToJson.toJsonMessage("fail","Controlla che tutti i campi siano presenti"));
            printWriter.close();
            return;
        }

        Ripetizione ripetizione = new Ripetizione();
        ripetizione.setIdutente(idUtente);
        ripetizione.setId(idRipetizione);
        ripetizione.setStatus(status);
        String value = ripetizioneController.updateRipetizioneStatus(ripetizione);

        if(value.equals("ok")){
            response.setStatus(HttpServletResponse.SC_OK);
            printWriter.write(ResponseToJson.toJsonMessage("success","Prenotazione aggiornata con successo"));
        }else{
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            printWriter.write(ResponseToJson.toJsonMessage("fail","Problema nel aggiornare la prenotazione",value));
        }
        printWriter.close();

    }
}
