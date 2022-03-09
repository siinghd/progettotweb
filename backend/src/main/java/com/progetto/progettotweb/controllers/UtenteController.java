package com.progetto.progettotweb.controllers;

import com.progetto.progettotweb.models.Utente;
import com.progetto.progettotweb.utils.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.Locale;

public class UtenteController {
    private DAO dao = null;
    public UtenteController(){
        this.dao = DAO.Singleton();
    }
    public String insertUtente(Utente user) {
        try {
            PreparedStatement pst = this.dao.getConn().prepareStatement(
                    "INSERT INTO utente (accountname,password,ruolo) values(?,?,?)");
            pst.setString(1,user.getAccountname().toLowerCase());
            pst.setString(2, BCrypt.hashpw(user.getPassword(),BCrypt.gensalt(10)));
            pst.setByte(3,user.getRuolo());
            int rowsAffected = pst.executeUpdate();
            if(rowsAffected>0){
                return "ok";
            }
            return "ok";
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }
    public String updateUtente(Utente user) {
        try {
            if(user.getId()==-1){
                return "Id non puo essere nullo, inserisci l'id";
            }else{
                PreparedStatement pst = this.dao.getConn().prepareStatement(
                        "UPDATE utente SET accountname=?,ruolo=? WHERE id=?");
                pst.setString(1,user.getAccountname());
                pst.setByte(2,user.getRuolo());
                pst.setInt(3,user.getId());

                int rowsAffected = pst.executeUpdate();
                if(rowsAffected>0){
                    return "ok";
                }
                return "ok";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }
    public String deleteUtente(Utente user) {
        try {
            if(user.getId()==-1){
                return "Id non puo essere nullo, inserisci l'id";
            }else{
                PreparedStatement pst = this.dao.getConn().prepareStatement(
                        "DELETE FROM utente WHERE id=?");
                pst.setInt(1,user.getId());
                int rowsAffected = pst.executeUpdate();
                if(rowsAffected>0){
                    return "ok";
                }
                return "ok";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }
    public ArrayList<Utente> queryDBUtente(String query) {
        ArrayList<Utente> out = new ArrayList<>();
        try {
            Statement st = this.dao.getConn().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Utente p = new Utente(  rs.getInt("id"), rs.getString("accountname"),
                        rs.getString("password"), rs.getByte("ruolo"));
                out.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return out;
    }
}
