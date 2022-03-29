package com.progetto.progettotweb.controllers;

import com.progetto.progettotweb.models.Corso;
import com.progetto.progettotweb.models.CorsoDocente;
import com.progetto.progettotweb.models.Docente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CorsoDocenteController {
    private DAO dao = null;
    public CorsoDocenteController(){
        this.dao = DAO.Singleton();
    }

    public String insertCorsoDocente(CorsoDocente corsoDocente) {
        try {
            PreparedStatement pst = this.dao.getConn().prepareStatement(
                    "INSERT INTO corsodocente (idcorso,iddocente) values(?,?)");
            pst.setInt(1,corsoDocente.getIdcorso());
            pst.setInt(2,corsoDocente.getIddocente());
            int rowsAffected = pst.executeUpdate();
            if(rowsAffected>0){
                System.out.println("CorsoDocente inserito correttamente");
            }
            return "ok";
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }

    public String deleteCorsoDocente(CorsoDocente corsoDocente) {
        try {
            if(corsoDocente.getIdcorso()==-1 && corsoDocente.getIddocente()!=-1){
                System.out.println("Id non puo essere nullo, inserisci l'id");
            }else{
                PreparedStatement pst = this.dao.getConn().prepareStatement(
                        "UPDATE corsodocente SET softdelete=? WHERE idcorso=? and iddocente=?");
                pst.setInt(1,corsoDocente.getSoftdelete());
                pst.setInt(2,corsoDocente.getIdcorso());
                pst.setInt(3,corsoDocente.getIddocente());

                int rowsAffected = pst.executeUpdate();
                if(rowsAffected>0){
                    System.out.println("CorsoDocente eliminato correttamente");
                }
            }
            return "ok";
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }
    public ArrayList<CorsoDocente> queryDBCorsoDocente() {
        ArrayList<CorsoDocente> out = new ArrayList<>();
        try {
            Statement st = this.dao.getConn().createStatement();
            ResultSet rs = st.executeQuery("SELECT c.id as cid , d.id AS did , c.softdelete as csoftdelete, d.softdelete as dsoftdelete, " +
                    "d.nome , d.cognome , c.titolo , idcorso , iddocente " +
                    "FROM corsodocente as cd " +
                    "JOIN corso as c ON cd.idcorso=c.id " +
                    "JOIN docente as d ON cd.iddocente= d.id " +
                    "WHERE cd.softdelete=0; ");
            while (rs.next()) {
                Docente p = new Docente(rs.getInt("iddocente"), rs.getString("nome"),
                        rs.getString("cognome"), rs.getInt("dsoftdelete"));
                Corso c = new Corso(rs.getInt("cid"), rs.getString("titolo"),rs.getInt("csoftdelete"));
                out.add(new CorsoDocente(p,c,0));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return out;
    }
    public ArrayList<CorsoDocente> queryDBCorsoDocenteByIdCorsoAndIdDocente(int idcorso,int iddocente) {
        ArrayList<CorsoDocente> out = new ArrayList<>();
        try {
            Statement st = this.dao.getConn().createStatement();
            ResultSet rs = st.executeQuery("SELECT c.id as cid , d.id AS did , c.softdelete as csoftdelete, d.softdelete as dsoftdelete, " +
                    "d.nome , d.cognome , c.titolo , idcorso , iddocente " +
                    "FROM corsodocente as cd " +
                    "JOIN corso as c ON cd.idcorso=c.id " +
                    "JOIN docente as d ON cd.iddocente= d.id " +
                    "WHERE cd.softdelete=0 AND idcorso="+idcorso+" AND iddocente="+iddocente);
            while (rs.next()) {
                Docente p = new Docente(rs.getInt("iddocente"), rs.getString("nome"),
                        rs.getString("cognome"), rs.getInt("dsoftdelete"));
                Corso c = new Corso(rs.getInt("cid"), rs.getString("titolo"),rs.getInt("csoftdelete"));
                out.add(new CorsoDocente(p,c,0));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return out;
    }
}
