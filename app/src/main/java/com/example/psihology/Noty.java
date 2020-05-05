package com.example.psihology;

import com.google.gson.Gson;

public class Noty {

    String text = "";

    public Noty(){}

    public Noty(String json){
        Gson gson = new Gson();
        Noty n = gson.fromJson(json, Noty.class);
        text = n.text;
    }

    public String toJson()
    {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;

    }



}
