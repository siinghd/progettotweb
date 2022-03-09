package com.progetto.progettotweb.routes.auth;

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

@WebServlet(name = "login", value = "/api/login")
public class Login extends HttpServlet {
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
        response.setContentType("application/json");
        if (email == null || email.isEmpty()) {
            printWriter.write("{\"status\":\"fail\", \"message\":\"Email e obbligatoria\",\"error\":\"PARAMETER NOT FOUND\"}");
            printWriter.close();
            return;

        }

        if (password == null || password.isEmpty()) {
            printWriter.write("{\"status\":\"fail\", \"message\":\"Password e obbligatoria\",\"error\":\"PARAMETER NOT FOUND\"}");
            printWriter.close();
            return;
        }
        ArrayList<Utente> user = utenteController.queryDBUtente("SELECT * FROM utente where accountname=\""+email+"\";");
        if(user.size()==0){
            printWriter.write("{\"status\":\"fail\", \"message\":\"Email o password non corrispondono\",\"error\":\"UNKWN\"}");
            printWriter.close();
            return;
        }
        Utente usr = user.get(0);
        if(BCrypt.checkpw(password,usr.getPassword())){
            usr.setPassword("");
            request.getSession().setAttribute("user",user);
            printWriter.write("{\"status\":\"success\", \"message\":\"Autenticazione eseguita\",\"data\":"+gson.toJson(user)+"}");
            printWriter.close();
        }else{
            printWriter.write("{\"status\":\"fail\", \"message\":\"Email o password non corrispondono\",\"error\":\"MATCH\"}");
            printWriter.close();
        }
    }
}
