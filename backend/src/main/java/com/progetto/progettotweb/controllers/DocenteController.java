package com.progetto.progettotweb.controllers;

import com.progetto.progettotweb.models.Docente;
import com.progetto.progettotweb.utils.BCrypt;

import java.sql.*;
import java.util.ArrayList;

public class  DocenteController {

    private DAO dao = null;
    public DocenteController(){
        this.dao = DAO.Singleton();
    }
    public int createUserForDocente(Docente docente) {
        Integer a = 2;
        try {
            PreparedStatement pst = this.dao.getConn().prepareStatement(
                    "INSERT INTO utente (accountname,password,ruolo) values(?,?,?)",Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, docente.getCognome()+ docente.getNome()+"@unito.it");
            pst.setString(2, BCrypt.hashpw(docente.getCognome()+ docente.getNome(),BCrypt.gensalt(10)));
            pst.setByte(3,a .byteValue());
            int rowsAffected = pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()){
                return rs.getInt(1);
            }
            rs.close();
            return -9;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return -9;
        }
    }
    public String insertDocente(Docente docente) {
        try {
            int iduser = this.createUserForDocente(docente);
            if(iduser==-9){
                return "Impossibile creare l'utente";
            }
            PreparedStatement pst = this.dao.getConn().prepareStatement(
                    "INSERT INTO docente (idutente,nome,cognome) values(?,?,?)");
            pst.setInt(1,iduser);
            pst.setString(2,docente.getNome());
            pst.setString(3,docente.getCognome());
            int rowsAffected = pst.executeUpdate();
            if(rowsAffected>0){
                return "ok";
            }
            return  "ok";
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }
    public void updateDocente(Docente docente) {
        try {
            if(docente.getId()==-1){
                System.out.println("Id non puo essere nullo, inserisci l'id");
            }else{
                PreparedStatement pst = this.dao.getConn().prepareStatement(
                        "UPDATE docente SET nome=?,cognome=? WHERE id=?");
                pst.setString(1,docente.getNome());
                pst.setString(2,docente.getCognome());
                pst.setInt(3,docente.getId());

                int rowsAffected = pst.executeUpdate();
                if(rowsAffected>0){
                    System.out.println("Docente aggiornato correttamente");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }
    public String deleteDocente(Docente docente) {
        try {
            if(docente.getId()==-1){
                System.out.println("Id non puo essere nullo, inserisci l'id");
            }else{
                PreparedStatement pst = this.dao.getConn().prepareStatement(
                        "UPDATE docente SET softdelete=? WHERE id=?");
                pst.setInt(1,docente.getSoftdelete());
                pst.setInt(2,docente.getId());
                int rowsAffected = pst.executeUpdate();
                if(rowsAffected>0){
                    System.out.println("Docente eliminato correttamente");
                }
            }
            return  "ok";
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }

    public ArrayList<Docente> queryDBDocente() {
        ArrayList<Docente> out = new ArrayList<>();
        try {
            Statement st = this.dao.getConn().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM docente where softdelete=0");
            while (rs.next()) {
                Docente p = new Docente(rs.getInt("id"), rs.getString("nome"),
                        rs.getString("cognome"),rs.getInt("softdelete"));
                out.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return out;
    }
}
