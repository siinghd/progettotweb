package com.progetto.progettotweb.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Ripetizione {
    private int id = -1;
    private Docente doc= null;
    private int iddoc = -1;
    private int idcorso = -1;
    private Corso corso = null;
    private String date = null;
    private String actualTime = null;
    private  String time = null;
    private int status = 1;
    private int idutente = -1;
    private Utente utente= null;
    public Ripetizione() {}
    public Ripetizione(int id , Docente doc, Corso corso, String date, String time,String actualTime,int status) {
        this.id = id;
        this.doc = doc;
        this.corso = corso;
        this.date = date;
        this.time = time;
        this.actualTime = actualTime;
        this.status= status;
    }
    public Ripetizione(int iddoc, int idcorso, int idutente, String date, String time, int status) {

        this.iddoc = iddoc;
        this.idcorso = idcorso;
        this.date = date;
        this.time = time;
        this.idutente = idutente;
        this.status= status;
    }
    public Ripetizione(int id , Docente doc, Corso corso, String date, String time ,int idutente,String actualTime,int status) {
        this.id = id;
        this.doc = doc;
        this.corso = corso;
        this.date = date;
        this.time = time;
        this.idutente = idutente;
        this.actualTime = actualTime;
        this.status= status;
    }
    public Ripetizione(Docente doc, Corso corso, String date, String time,String actualTime,int status) {
        this.doc = doc;
        this.corso = corso;
        this.date = date;
        this.time = time;
        this.actualTime = actualTime;
        this.status= status;
    }
    public Ripetizione(Docente doc, Corso corso, String date, String time, int idutente,String actualTime,int status) {
        this.doc = doc;
        this.corso = corso;
        this.date = date;
        this.time = time;
        this.idutente = idutente;
        this.actualTime = actualTime;
        this.status= status;
    }
    public Docente getDoc() {
        return doc;
    }

    public void setDoc(Docente doc) {
        this.doc = doc;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Corso getCorso() {
        return corso;
    }

    public void setCorso(Corso corso) {
        this.corso = corso;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdutente() {
        return idutente;
    }

    public void setIdutente(int idutente) {
        this.idutente = idutente;
    }

    public int getStatus() {
        return status;
    }

    public int getIddoc() {return iddoc;}

    public void setIddoc(int iddoc) {this.iddoc = iddoc;}

    public int getIdcorso() {return idcorso;}

    public void setIdcorso(int idcorso) {this.idcorso = idcorso;}

    public void setStatus(int status) {
        this.status = status;
    }
    public String getActualTime() {
        return actualTime;
    }

    public void setActualTime(String actualTime) {
        this.actualTime = actualTime;
    }

    @Override
    public String toString() {
        return "Ripetizione{" +
                "id=" + id +
                ", " + doc +
                ", " + corso +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", idutente=" + idutente +
                '}';
    }


}
