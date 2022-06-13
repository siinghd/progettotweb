package com.progetto.progettotweb.routes;

import com.google.gson.Gson;
import com.progetto.progettotweb.controllers.UtenteController;
import com.progetto.progettotweb.models.Utente;
import com.progetto.progettotweb.utils.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;

@WebServlet(name = "register", value = "/api/register")
public class Register extends HttpServlet {
    private UtenteController utenteController = new UtenteController();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Gson gson = new Gson();
        PrintWriter printWriter = response.getWriter();
        if (email == null || email.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            printWriter.write("{\"status\":\"fail\", \"message\":\"Email e obbligatoria\",\"error\":\"PARAMETER NOT FOUND\"}");
            printWriter.close();
            return;

        }

        if (password == null || password.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            printWriter.write("{\"status\":\"fail\", \"message\":\"Password e obbligatoria\",\"error\":\"PARAMETER NOT FOUND\"}");
            printWriter.close();
            return;
        }
        int ruolo = 1;
        ArrayList<Utente> user  = utenteController.queryDBUtente("Select * from utente where accountname='"+email.toLowerCase()+"'");
        if(user.size()>0){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            printWriter.write("{\"status\":\"fail\", \"message\":\"Utente e gia presente con questa email\",\"error\":\"\"}");
            printWriter.close();
            return;
        }
        String result = utenteController.insertUtente(new Utente(email.toLowerCase(),password, (byte) ruolo));
        if(result.equals("ok")){
            response.setStatus(HttpServletResponse.SC_OK);
            printWriter.write("{\"status\":\"success\", \"message\":\"Registrazione eseguita\"}");
            printWriter.close();
        }else{
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            printWriter.write("{\"status\":\"fail\", \"message\":\""+result+"\",\"error\":\"\"}");
            printWriter.close();
        }
    }
}