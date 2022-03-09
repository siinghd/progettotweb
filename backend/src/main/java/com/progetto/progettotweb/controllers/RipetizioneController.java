package com.progetto.progettotweb.controllers;

import com.progetto.progettotweb.models.Corso;
import com.progetto.progettotweb.models.Docente;
import com.progetto.progettotweb.models.Ripetizione;
import utils.Utils;

import java.sql.*;
import java.util.ArrayList;

public class RipetizioneController {

    private DAO dao = null;
    public RipetizioneController(){
        this.dao = DAO.Singleton();
    }

    public void insertRipetizione(Ripetizione ripetizione) {
        System.out.println(this.toString());
        try {
            if(ripetizione.getIdutente()==-1){
                System.out.println("Nessun id utente presente");
                return;
            }
            PreparedStatement pst = this.dao.getConn().prepareStatement(
                    "INSERT INTO ripetizione (idcorso,iddocente,idutente,data,ora) values(?,?,?,?,?)");
            pst.setInt(1,ripetizione.getCorso().getId());
            pst.setInt(2,ripetizione.getDoc().getId());
            pst.setInt(3,ripetizione.getIdutente());
            pst.setString(4,ripetizione.getDate());
            pst.setString(5,ripetizione.getActualTime());

            int rowsAffected = pst.executeUpdate();
            if(rowsAffected>0){
                System.out.println("Ripetizione inserita correttamente");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }
    public void updateRipetizione(Ripetizione ripetizione) {
        try {
            if(ripetizione.getId()==-1){
                System.out.println("Id non puo essere nullo, inserisci l'id");
            }else{

                PreparedStatement pst = this.dao.getConn().prepareStatement(
                        "UPDATE ripetizione SET idcorso=?,iddocente=? ,idutente=?,data=?,ora=?,status=?WHERE id=?");
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
    public void deleteRipetizione(Ripetizione ripetizione) {
        try {
            if(ripetizione.getId()==-1){
                System.out.println("Id non puo essere nullo, inserisci l'id");
            }else{
                PreparedStatement pst = this.dao.getConn().prepareStatement(
                        "DELETE FROM ripetizione WHERE id=?");
                pst.setInt(1,ripetizione.getId());
                int rowsAffected = pst.executeUpdate();
                if(rowsAffected>0){
                    System.out.println("Ripetizione eliminato correttamente");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Ripetizione> queryDBRipetizioni() {
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
                    ResultSet rs = st.executeQuery("SELECT c.id as cid , d.id AS did , d.nome , d.cognome , c.titolo , idcorso , iddocente FROM corsodocente as cd " +
                            "JOIN corso as c ON cd.idcorso=c.id " +
                            "JOIN docente as d ON cd.iddocente= d.id " +
                            "WHERE NOT EXISTS " +
                            "(SELECT * from ripetizione as r " +
                            "WHERE r.idcorso = cd.idcorso AND r.iddocente = cd.iddocente AND data='"+dateAvaible +
                            "' AND ora='"+j+":00' )");


                    while (rs.next()) {
                        Docente p = new Docente(rs.getInt("iddocente"), rs.getString("nome"),
                                rs.getString("cognome"));
                        Corso c = new Corso(rs.getInt("cid"), rs.getString("titolo"));
                        out.add(new Ripetizione(p, c, dateAvaible, j+":00 - "+(j+1)+":00",j+":00",1));
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return out;
    }
}
