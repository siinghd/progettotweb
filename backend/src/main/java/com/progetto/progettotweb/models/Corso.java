package com.progetto.progettotweb.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Corso {
    private int id = -1;
    private String titolo= null;

    public Corso() {}
    public Corso(String titolo) {
        this.titolo = titolo;
    }
    public Corso(int id, String titolo) {
        this.id = id;
        this.titolo = titolo;
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

    @Override
    public String toString() {
        return "Corso{" +
                "id=" + id +
                ", titolo='" + titolo + '\'' +
                '}';
    }
}
