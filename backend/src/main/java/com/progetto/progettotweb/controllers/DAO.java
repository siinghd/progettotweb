package com.progetto.progettotweb.controllers;


import java.sql.*;


public class DAO {
    private static  DAO instance = null;
    private static final String url1 = "jdbc:mysql://localhost:3306/progettouniitum?autoReconnect=true&useSSL=false";
    private static final String user = "randomproject";
    private static final String password = "!Password1990";
    private Connection conn = null;
    private DAO(){}
    public static DAO Singleton()
    {
        // To ensure only one instance is created
        if (instance == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                System.out.println("Driver Caricato correttamente");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            instance = new DAO();
        }
        return instance;
    }

    public Connection getConn() {
        try {
            if (this.conn == null) {
                this.conn = DriverManager.getConnection(url1, user, password);
            }
        } catch (SQLException e) {
            System.out.println(e);
            System.exit(1);
        }
        return this.conn;
    }

    public void closeConnection() {
        if (conn == null) {
            System.out.println("La connessione è gia stata chiusa");
        } else {
            try {
                this.conn.close();
                System.out.println("Connessione chiusa");
            } catch (SQLException e) {
                System.out.println("Problema con la chiusura della connessione");
                System.out.println(e.getMessage());
            }
        }
    }

}
