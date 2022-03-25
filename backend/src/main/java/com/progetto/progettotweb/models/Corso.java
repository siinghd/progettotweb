package com.progetto.progettotweb.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Corso {
    private int id = -1;
    private String titolo= null;
    private int softdelete = 0;



    public Corso() {}
    public Corso(String titolo, int softdelete) {
        this.titolo = titolo;
        this.softdelete = softdelete;
    }
    public Corso(int id, String titolo,int softdelete) {
        this.id = id;
        this.titolo = titolo;
        this.softdelete = softdelete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public int getSoftdelete() {
        return softdelete;
    }

    public void setSoftdelete(int softdelete) {
        this.softdelete = softdelete;
    }
    @Override
    public String toString() {
        return "Corso{" +
                "id=" + id +
                ", titolo='" + titolo + '\'' +
                '}';
    }
}
