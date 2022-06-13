package com.progetto.progettotweb.routes.auth.admin;
import com.google.gson.Gson;
import com.progetto.progettotweb.controllers.CorsoDocenteController;
import com.progetto.progettotweb.models.Corso;
import com.progetto.progettotweb.models.CorsoDocente;
import com.progetto.progettotweb.utils.ResponseToJson;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

@WebServlet(name = "courseprofessor", value = "/api/auth/admin/courseprofessor")
public class RouteCorsoDocente extends HttpServlet {
    private final CorsoDocenteController corsoDocenteController= new CorsoDocenteController();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter printWriter = response.getWriter();
        ArrayList<CorsoDocente> corsoDocenti = corsoDocenteController.queryDBCorsoDocente();
        response.setStatus(HttpServletResponse.SC_OK);
        printWriter.write(ResponseToJson.toJsonMessage("success","Ricerca Corsi associati ai docenti",corsoDocenti));
        printWriter.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idcorso = -1 ;
        int iddocente = -1 ;
        PrintWriter printWriter = response.getWriter();
        try{
            idcorso = Integer.parseInt(request.getParameter("idcorso"));
            iddocente = Integer.parseInt(request.getParameter("iddocente"));
        }catch (Exception e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            printWriter.write(ResponseToJson.toJsonMessage("fail","Controlla bene i dati inseriti, si e verificato un errore di parsing"));
            printWriter.close();
            return;
        }
        if (idcorso ==-1  || iddocente == -1) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            printWriter.write(ResponseToJson.toJsonMessage("fail","Controlla che tutti i campi siano presenti"));
            printWriter.close();
            return;
        }

        ArrayList<CorsoDocente> corsoDocente = corsoDocenteController.queryDBCorsoDocenteByIdCorsoAndIdDocente(idcorso,iddocente);
        if(corsoDocente.size()>0){
            printWriter.write(ResponseToJson.toJsonMessage("fail","Il Corso associato esiste gia nel sistema"));
            printWriter.close();
            return;
        }

        CorsoDocente corsodocente = new CorsoDocente(idcorso,iddocente,0);
        String value = corsoDocenteController.insertCorsoDocente(corsodocente);
        if(value.equals("ok")){
            response.setStatus(HttpServletResponse.SC_OK);
            printWriter.write(ResponseToJson.toJsonMessage("success","Corso associato al docente creato con successo",corsodocente));
        }else{
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            printWriter.write(ResponseToJson.toJsonMessage("fail","Problema nel creare il corso associato al docente",value));
        }
        printWriter.close();
    }
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idcorso = -1;
        int iddocente = -1;
        PrintWriter printWriter = response.getWriter();
        try{
            idcorso = Integer.parseInt(request.getParameter("idcorso"));
            iddocente = Integer.parseInt(request.getParameter("iddocente"));
        }catch(Exception e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            printWriter.write(ResponseToJson.toJsonMessage("fail","Controlla che tutti i campi siano presenti"));
            printWriter.close();
            return;
        }
        CorsoDocente corsoDocente = new CorsoDocente(idcorso,iddocente,1);
        String value = corsoDocenteController.deleteCorsoDocente(corsoDocente);
        if(value.equals("ok")){
            response.setStatus(HttpServletResponse.SC_OK);
            printWriter.write(ResponseToJson.toJsonMessage("success","Corso assocaito al docente Eliminato con successo"));
        }else{
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            printWriter.write(ResponseToJson.toJsonMessage("fail","Problema nel eliminare il corso assocaito al docente con"));
        }
        printWriter.close();
    }
}
