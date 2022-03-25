package com.progetto.progettotweb.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Docente {
    private int id = -1;
    private String nome= null;
    private String cognome = null;
    private int softdelete = 0;

    public Docente() {}
    public Docente(String nome, String cognome) {
        this.nome = nome;
        this.cognome = cognome;
    }
    public Docente(String nome, String cognome, int softdelete) {
        this.nome = nome;
        this.cognome = cognome;
        this.softdelete = softdelete;
    }
    public Docente(int id, String nome, String cognome , int softdelete) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.softdelete = softdelete;
    }

    public int getSoftdelete() {
        return softdelete;
    }

    public void setSoftdelete(int softdelete) {
        this.softdelete = softdelete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    @Override
    public String toString() {
        return "Docente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                '}';
    }


}
