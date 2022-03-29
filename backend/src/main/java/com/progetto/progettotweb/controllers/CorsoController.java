package com.progetto.progettotweb.controllers;

import com.progetto.progettotweb.models.Corso;

import java.sql.*;
import java.util.ArrayList;

public class CorsoController {
    private DAO dao = null;
    public CorsoController(){
        this.dao = DAO.Singleton();
    }

    public String insertCorso(Corso corso) {
        try {
            PreparedStatement pst = this.dao.getConn().prepareStatement(
                    "INSERT INTO corso (titolo) values(?)");
            pst.setString(1,corso.getTitolo());

            int rowsAffected = pst.executeUpdate();
            if(rowsAffected>0){
                System.out.println("Corso inserito correttamente");
            }
            return "ok";
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }
    public void updateCorso(Corso corso) {
        try {
            if(corso.getId()==-1){
                System.out.println("Id non puo essere nullo, inserisci l'id");
            }else{
                PreparedStatement pst = this.dao.getConn().prepareStatement(
                        "UPDATE corso SET titolo=? WHERE id=?");
                pst.setString(1,corso.getTitolo());
                pst.setInt(2,corso.getId());

                int rowsAffected = pst.executeUpdate();
                if(rowsAffected>0){
                    System.out.println("Corso aggiornato correttamente");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }
    public String deleteCorso(Corso corso) {
        try {
            if(corso.getId()==-1){
                System.out.println("Id non puo essere nullo, inserisci l'id");
            }else{
                PreparedStatement pst = this.dao.getConn().prepareStatement(
                        "UPDATE corso SET softdelete=? WHERE id=?");
                pst.setInt(1,corso.getSoftdelete());
                pst.setInt(2,corso.getId());
                int rowsAffected = pst.executeUpdate();
                if(rowsAffected>0){
                    System.out.println("Corso eliminato correttamente");
                }
            }
            return "ok";
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }
    public ArrayList<Corso> queryDBCorso() {
        ArrayList<Corso> out = new ArrayList<>();
        try {
            Statement st = this.dao.getConn().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM corso where softdelete=0");
            while (rs.next()) {
                Corso p = new Corso(rs.getInt("id"), rs.getString("titolo") ,rs.getInt("softdelete"));
                out.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return out;
    }
    public ArrayList<Corso> queryDBCorsoByName(String name) {
        ArrayList<Corso> out = new ArrayList<>();
        try {
            Statement st = this.dao.getConn().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM corso where softdelete=0 AND titolo='"+name+"'");
            while (rs.next()) {
                Corso p = new Corso(rs.getInt("id"), rs.getString("titolo") ,rs.getInt("softdelete"));
                out.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return out;
    }
}
