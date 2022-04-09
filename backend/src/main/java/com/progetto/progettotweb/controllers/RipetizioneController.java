package com.progetto.progettotweb.controllers;

import com.progetto.progettotweb.models.Corso;
import com.progetto.progettotweb.models.Docente;
import com.progetto.progettotweb.models.Ripetizione;
import com.progetto.progettotweb.models.Utente;
import utils.Utils;

import java.sql.*;
import java.util.ArrayList;

public class RipetizioneController {

    private DAO dao = null;
    public RipetizioneController(){
        this.dao = DAO.Singleton();
    }

    public String insertRipetizione(Ripetizione ripetizione) {
        try {
            if(ripetizione.getIdutente()==-1){
                System.out.println("Nessun id utente presente");
                return "fail";
            }
            PreparedStatement pst = this.dao.getConn().prepareStatement(
                    "INSERT INTO ripetizione (idcorso,iddocente,idutente,data,ora,status) values(?,?,?,?,?,?)");
            pst.setInt(1,ripetizione.getIdcorso());
            pst.setInt(2,ripetizione.getIddoc());
            pst.setInt(3,ripetizione.getIdutente());
            pst.setString(4,ripetizione.getDate());
            pst.setString(5,ripetizione.getTime());
            pst.setInt(6,ripetizione.getStatus());

            int rowsAffected = pst.executeUpdate();
            if(rowsAffected>0){
                System.out.println("Ripetizione inserita correttamente");
            }
            return "ok";
        } catch (SQLException e) {
            return e.getMessage();

        }
    }
    public void updateRipetizione(Ripetizione ripetizione) {
        try {
            if(ripetizione.getId()==-1){
                System.out.println("Id non puo essere nullo, inserisci l'id");
            }else{

                PreparedStatement pst = this.dao.getConn().prepareStatement(
                        "UPDATE ripetizione SET idcorso=?,iddocente=?,idutente=?,data=?,ora=?,status=?WHERE id=?");
                pst.setInt(1,ripetizione.getCorso().getId());
                pst.setInt(2,ripetizione.getDoc().getId());
                pst.setInt(3,ripetizione.getIdutente());
                pst.setString(4,ripetizione.getDate());
                pst.setString(5,ripetizione.getActualTime());
                pst.setInt(6,ripetizione.getStatus());
                pst.setInt(7,ripetizione.getId());


                int rowsAffected = pst.executeUpdate();
                if(rowsAffected>0){
                    System.out.println("Ripetizione aggiornato correttamente");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }
    public String updateRipetizioneStatus(Ripetizione ripetizione) {
        try {
            if(ripetizione.getId()==-1){
                System.out.println("Id non puo essere nullo, inserisci l'id");
            }else{

                PreparedStatement pst = this.dao.getConn().prepareStatement(
                        "UPDATE ripetizione SET status=? WHERE id=? AND idutente=?");
                pst.setInt(1,ripetizione.getStatus());
                pst.setInt(2,ripetizione.getId());
                pst.setInt(3,ripetizione.getIdutente());

                int rowsAffected = pst.executeUpdate();
                if(rowsAffected>0){
                    System.out.println("Ripetizione aggiornato correttamente");
                }
            }
            return "ok";
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }
    public String deleteRipetizione(int id) {
        try {
            if(id==-1){
                System.out.println("Id non puo essere nullo, inserisci l'id");
                return "Id non puo essere nullo, inserisci l'id";
            }else{
                PreparedStatement pst = this.dao.getConn().prepareStatement(
                        "DELETE FROM ripetizione WHERE id=?");
                pst.setInt(1,id);
                int rowsAffected = pst.executeUpdate();
                if(rowsAffected>0){
                    System.out.println("Ripetizione eliminato correttamente");
                }
                return "ok";
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }

    public ArrayList<Ripetizione> queryDBRipetizioniDisponibili() {
        ArrayList<Ripetizione> out = new ArrayList<>();
        ArrayList<String> days = Utils.getDates();
        int startingtime= 15;
        int endtime= 19;
        try {
            Statement st = this.dao.getConn().createStatement();
            for (int i = 0; i < days.size(); i++) {
                String[] split = days.get(i).split("-");
                String dateAvaible = split[2] + "-" + split[1] + "-" + split[0];
                for (int j = startingtime; j < endtime; j++) {
                    ResultSet rs = st.executeQuery("SELECT c.id as cid , d.id AS did , d.nome , d.cognome , c.titolo , idcorso , iddocente , c.softdelete , d.softdelete  FROM corsodocente as cd " +
                            "JOIN corso as c ON cd.idcorso=c.id " +
                            "JOIN docente as d ON cd.iddocente= d.id " +
                            "WHERE c.softdelete = 0 AND d.softdelete = 0 AND cd.softdelete=0 AND NOT EXISTS " +
                            "(SELECT * from ripetizione as r " +
                            "WHERE r.iddocente = cd.iddocente AND data='"+dateAvaible +
                            /*"WHERE r.idcorso = cd.idcorso AND r.iddocente = cd.iddocente AND data='"+dateAvaible +*/
                            "' AND ora='"+j+":00' )  ") ;




                    while (rs.next()) {
                        Docente p = new Docente(rs.getInt("iddocente"), rs.getString("nome"),
                                rs.getString("cognome"), rs.getInt("softdelete"));
                        Corso c = new Corso(rs.getInt("cid"), rs.getString("titolo"),rs.getInt("softdelete"));
                        out.add(new Ripetizione(p, c, dateAvaible, j+":00 - "+(j+1)+":00",j+":00",1));
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return out;
    }

    public ArrayList<Ripetizione> queryDBRipetizioniPrenotate(int id,int limit,int currpage) {
        ArrayList<Ripetizione> out = new ArrayList<>();


        try {
            Statement st = this.dao.getConn().createStatement();

            ResultSet rs = st.executeQuery("SELECT r.id , r.id , r.iddocente , r.idcorso as cid , r.data , r.ora , r.status , r.idutente , c.titolo , d.nome , d.cognome , d.softdelete , c.softdelete from ripetizione as r " +
                                                    " JOIN corso as c ON r.idcorso = c.id" +
                                                    " JOIN docente as d ON r.iddocente = d.id" +
                                                    " WHERE r.idutente = " + id + " LIMIT "+ currpage*limit + ","+limit);
            while (rs.next()) {
                Docente p = new Docente(rs.getInt("iddocente"), rs.getString("nome"),
                        rs.getString("cognome"), rs.getInt("softdelete"));
                Corso c = new Corso(rs.getInt("cid"), rs.getString("titolo"),rs.getInt("softdelete"));
                out.add(new Ripetizione(rs.getInt("id"), p, c, rs.getString("data"), rs.getString("ora"),rs.getInt("idutente"), "15:00",rs.getInt("status")));
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return out;
    }
    public ArrayList<Ripetizione> queryDBRipetizioniByIdDateTime(int id, String date, String time) {
        ArrayList<Ripetizione> out = new ArrayList<>();


        try {
            Statement st = this.dao.getConn().createStatement();

            ResultSet rs = st.executeQuery("SELECT r.id , r.id , r.iddocente , r.idcorso as cid , r.data , r.ora , r.status , r.idutente , c.titolo , d.nome , d.cognome , d.softdelete , c.softdelete from ripetizione as r " +
                    " JOIN corso as c ON r.idcorso = c.id" +
                    " JOIN docente as d ON r.iddocente = d.id" +
                    " WHERE r.idutente = " + id + " AND r.data = '"+date+"' AND ora='"+time+"'");
            while (rs.next()) {
                Docente p = new Docente(rs.getInt("iddocente"), rs.getString("nome"),
                        rs.getString("cognome"), rs.getInt("softdelete"));
                Corso c = new Corso(rs.getInt("cid"), rs.getString("titolo"),rs.getInt("softdelete"));
                out.add(new Ripetizione(rs.getInt("id"), p, c, rs.getString("data"), rs.getString("ora"),rs.getInt("idutente"), "15:00",rs.getInt("status")));
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return out;
    }
    public ArrayList<Ripetizione> queryDBRipetizioniAll(int limit,int currpage) {
        ArrayList<Ripetizione> out = new ArrayList<>();


        try {
            Statement st = this.dao.getConn().createStatement();

            ResultSet rs = st.executeQuery("SELECT u.id as uid,u.accountname,u.ruolo, r.id , r.id , r.iddocente , r.idcorso as cid , r.data , r.ora , r.status , r.idutente , c.titolo , d.nome , d.cognome , d.softdelete , c.softdelete from ripetizione as r " +
                    " JOIN corso as c ON r.idcorso = c.id" +
                    " JOIN docente as d ON r.iddocente = d.id"+
                    " JOIN utente as u ON r.idutente = u.id"+
                    " LIMIT "+ currpage*limit + ","+limit);

            while (rs.next()) {
                Docente p = new Docente(rs.getInt("iddocente"), rs.getString("nome"),
                        rs.getString("cognome"), rs.getInt("softdelete"));
                Corso c = new Corso(rs.getInt("cid"), rs.getString("titolo"),rs.getInt("softdelete"));
                Ripetizione ripet= new Ripetizione(rs.getInt("id"), p, c, rs.getString("data"), rs.getString("ora"),rs.getInt("idutente"), "15:00",rs.getInt("status"));
                ripet.setUtente(new Utente(rs.getInt("uid"),rs.getString("u.accountname"),null,rs.getByte("u.ruolo")));
                out.add(ripet);
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return out;
    }
}
