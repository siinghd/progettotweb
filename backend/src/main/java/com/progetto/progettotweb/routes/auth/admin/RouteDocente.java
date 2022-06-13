package com.progetto.progettotweb.routes.auth.admin;
import com.google.gson.Gson;
import com.progetto.progettotweb.controllers.DocenteController;
import com.progetto.progettotweb.models.Docente;
import com.progetto.progettotweb.utils.ResponseToJson;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "docente", value = "/api/auth/admin/docente")
public class RouteDocente extends HttpServlet {
    private final DocenteController docenteController= new DocenteController();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter printWriter = response.getWriter();
        ArrayList<Docente> professors = docenteController.queryDBDocente();
        response.setStatus(HttpServletResponse.SC_OK);
        printWriter.write(ResponseToJson.toJsonMessage("success","Ricerca docenti",professors));
        printWriter.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("name");
        String cognome = request.getParameter("lastname");
        PrintWriter printWriter = response.getWriter();
        if (nome == null || cognome == null || nome.equals("") || cognome.equals("")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            printWriter.write(ResponseToJson.toJsonMessage("fail","Controlla che tutti i campi siano presenti"));
            printWriter.close();
            return;

        }
        Docente doc = new Docente(nome,cognome);
        String value = docenteController.insertDocente(doc);
        if(value.equals("ok")){
            response.setStatus(HttpServletResponse.SC_OK);
            printWriter.write(ResponseToJson.toJsonMessage("success","Docente creato con successo",doc));
        }else{
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            printWriter.write(ResponseToJson.toJsonMessage("fail","Problema nel creare il docente",value));
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
        Docente doc = new Docente(id,"nome","cognome",1);
        String value = docenteController.deleteDocente(doc);
        if(value.equals("ok")){
            response.setStatus(HttpServletResponse.SC_OK);
            printWriter.write(ResponseToJson.toJsonMessage("success","Docente Eliminato con successo"));
        }else{
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            printWriter.write(ResponseToJson.toJsonMessage("fail","Problema nel eliminare il docente con id: "+ id));
        }
        printWriter.close();
    }
}
