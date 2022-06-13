package com.progetto.progettotweb.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CorsoDocente {
    private int idcorso = -1;
    private int iddocente= -1;
    private int softdelete = 0;
    private Docente docente= null;
    private Corso corso= null;

    public CorsoDocente() {}
    public CorsoDocente(int idcorso, int iddocente , int softdelete) {
        this.idcorso = idcorso;
        this.iddocente = iddocente;
        this.softdelete = softdelete;
    }
    public CorsoDocente(Docente docente, Corso corso , int softdelete) {
        this.docente = docente;
        this.corso = corso;
        this.softdelete = softdelete;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public Corso getCorso() {
        return corso;
    }

    public void setCorso(Corso corso) {
        this.corso = corso;
    }

    public int getSoftdelete() {
        return softdelete;
    }

    public void setSoftdelete(int softdelete) {
        this.softdelete = softdelete;
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
