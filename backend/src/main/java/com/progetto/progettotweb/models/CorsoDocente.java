package com.progetto.progettotweb.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CorsoDocente {
    private int idcorso = -1;
    private int iddocente= -1;

    public CorsoDocente() {}
    public CorsoDocente(int idcorso, int iddocente) {
        this.idcorso = idcorso;
        this.iddocente = iddocente;
    }

    public int getIdcorso() {
        return idcorso;
    }

    public void setIdcorso(int idcorso) {
        this.idcorso = idcorso;
    }

    public int getIddocente() {
        return iddocente;
    }

    public void setIddocente(int iddocente) {
        this.iddocente = iddocente;
    }

    @Override
    public String toString() {
        return "CorsoDocente{" +
                "idcorso=" + idcorso +
                ", iddocente=" + iddocente +
                '}';
    }

}
