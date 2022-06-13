package com.progetto.progettotweb.utils;

import com.google.gson.Gson;

public class ResponseToJson {
    public static String toJsonMessage(String status){
        return "{\"status\":\""+status+"\"}";
    }
    public static String toJsonMessage(String status,String message){
        return "{\"status\":\""+status+"\", \"message\":\""+message+"\"}";
    }
    public static  String toJsonMessage(String status, String message, String error){
        return "{\"status\":\""+status+"\", \"message\":\""+message+"\",\"error\":"+error+"}";
    }
    public static <T> String toJsonMessage(String status, String message, T data){
        Gson gson = new Gson();
        return "{\"status\":\""+status+"\", \"message\":\""+message+"\",\"data\":"+gson.toJson(data)+"}";
    }
    public static <T> String toJsonMessage(String status, String message, String error, T data){
        Gson gson = new Gson();
        return "{\"status\":\""+status+"\", \"message\":\""+message+"\",\"error\": \""+error+"\",\"data\":"+gson.toJson(data)+"}";
    }
}
