package com.example.psihology;

import com.google.gson.Gson;

public class Client {

    public String name = "";
    public int age = 0;
    public String phone = "";
    public String biography = "";
    public String question = "";
    public String anamnez = "";
    public String note = "";

    public Client() { }

    public Client(String json)
    {
        Gson gson = new Gson();
        Client c = gson.fromJson(json, Client.class);
        name = c.name;
        age = c.age;
        phone = c.phone;
        biography = c.biography;
        question = c.question;
        anamnez = c.anamnez;
        note = c.note;
    }

    public String toJson()
    {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
    }
}
