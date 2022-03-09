package com.progetto.progettotweb.controllers;

import com.progetto.progettotweb.models.CorsoDocente;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CorsoDocenteController {
    private DAO dao = null;
    public CorsoDocenteController(){
        this.dao = DAO.Singleton();
    }

    public void insertCorsoDocente(CorsoDocente corsoDocente) {
        try {
            PreparedStatement pst = this.dao.getConn().prepareStatement(
                    "INSERT INTO corsodocente (idcorso,iddocente) values(?,?)");
            pst.setInt(1,corsoDocente.getIdcorso());
            pst.setInt(2,corsoDocente.getIddocente());
            int rowsAffected = pst.executeUpdate();
            if(rowsAffected>0){
                System.out.println("CorsoDocente inserito correttamente");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }

    public void deleteCorsoDocente(CorsoDocente corsoDocente) {
        try {
            if(corsoDocente.getIdcorso()==-1 && corsoDocente.getIddocente()!=-1){
                System.out.println("Id non puo essere nullo, inserisci l'id");
            }else{
                PreparedStatement pst = this.dao.getConn().prepareStatement(
                        "DELETE FROM corsodocente WHERE idcorso=? and iddocente=?");
                pst.setInt(1,corsoDocente.getIdcorso());
                pst.setInt(1,corsoDocente.getIddocente());

                int rowsAffected = pst.executeUpdate();
                if(rowsAffected>0){
                    System.out.println("CorsoDocente eliminato correttamente");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
