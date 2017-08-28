package com.example.gretchen.agrodata.data.model;

/**
 * Created by Raisac on 8/24/2017.
 */

public class pokeTheServer {
    public static String Id;
    public static String Text;

    public pokeTheServer(){

    }


    public static String getText() {  return Text; }

    public static void setText(String text) {
        Text = text;
    }

    public static String getId() {
        return Id;
    }

    public static void setId(String id) {
        Id = id;
    }
}
