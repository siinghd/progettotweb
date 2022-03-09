package com.progetto.progettotweb.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Utente {
    private int id = -1;
    private String accountname= null;
    private String password = null;
    private Byte ruolo = null;

    public Utente() {}
    public Utente(String accountname, String password, Byte ruolo) {
        this.accountname = accountname;
        this.password = password;
        this.ruolo = ruolo;
    }
    public Utente(int id, String accountname, String password, Byte ruolo) {
        this.id = id;
        this.accountname = accountname;
        this.password = password;
        this.ruolo = ruolo;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountname() {
        return accountname;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Byte getRuolo() {
        return ruolo;
    }

    public void setRuolo(Byte ruolo) {
        this.ruolo = ruolo;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "id=" + id +
                ", accountname='" + accountname + '\'' +
                ", password='" + password + '\'' +
                ", ruolo=" + ruolo +
                '}';
    }



}
