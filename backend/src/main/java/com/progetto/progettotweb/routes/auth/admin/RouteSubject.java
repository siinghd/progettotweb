package com.progetto.progettotweb.routes.auth.admin;
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

@WebServlet(name = "subject", value = "/api/auth/admin/subject")
public class RouteSubject extends HttpServlet {
    private final CorsoController corsoController= new CorsoController();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter printWriter = response.getWriter();
        ArrayList<Corso> subjects = corsoController.queryDBCorso();
        response.setStatus(HttpServletResponse.SC_OK);
        printWriter.write(ResponseToJson.toJsonMessage("success","Ricerca Corsi",subjects));
        printWriter.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subjectName = request.getParameter("name");
        PrintWriter printWriter = response.getWriter();
        if (subjectName == null || subjectName.equals("")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            printWriter.write(ResponseToJson.toJsonMessage("fail","Controlla che tutti i campi siano presenti"));
            printWriter.close();
            return;

        }
        ArrayList<Corso> subjects = corsoController.queryDBCorsoByName(subjectName);
        if(subjects.size()>0){
            printWriter.write(ResponseToJson.toJsonMessage("fail","Il corso esiste gia nel sistema"));
            printWriter.close();
            return;
        }

        Corso subject = new Corso(subjectName,0);
        String value = corsoController.insertCorso(subject);
        if(value.equals("ok")){
            response.setStatus(HttpServletResponse.SC_OK);
            printWriter.write(ResponseToJson.toJsonMessage("success","Corso creato con successo",subject));
        }else{
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            printWriter.write(ResponseToJson.toJsonMessage("fail","Problema nel creare il corso",value));
        }
        printWriter.close();
    }
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = -1;
        PrintWriter printWriter = response.getWriter();
        try{
            id = Integer.parseInt(request.getParameter("id"));
        }catch(Exception e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            printWriter.write(ResponseToJson.toJsonMessage("fail","Controlla che tutti i campi siano presenti"));
            printWriter.close();
            return;
        }
        Corso documnt = new Corso(id,"nome",1);
        String value = corsoController.deleteCorso(documnt);
        if(value.equals("ok")){
            response.setStatus(HttpServletResponse.SC_OK);
            printWriter.write(ResponseToJson.toJsonMessage("success","Corso Eliminato con successo"));
        }else{
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            printWriter.write(ResponseToJson.toJsonMessage("fail","Problema nel eliminare il corso con id: "+ id));
        }
        printWriter.close();
    }
}
