package com.example.psihology;

import android.content.Context;
import android.util.Pair;

import java.util.ArrayList;


public class PsyhoKeeper
{
    private static ArrayList<Pair<Integer, Client>> allClients;
    private static ArrayList<Pair<Integer, Meeting>> allMeetings;

    public PsyhoKeeper(Context context)
    {
        DataBaseWorker dw = new DataBaseWorker(context);
        allClients =  dw.getAllClientsFromDB();
        allMeetings = dw.getAllMeetingsFromDB();
    }

    public ArrayList<Pair<Integer, Client>> getClientsWhere(Client template)
    {
        return null;
    }
    public ArrayList<Pair<Integer, Meeting>> getMeetingsWhere(Meeting template) { return null; }

    public ArrayList<Pair<Integer, Client>> getAllClients()
    {
        return allClients;
    }
    public ArrayList<Pair<Integer, Meeting>> getAllMeetings() {return  allMeetings;}

    public Client getClientById(int id){
        for (Pair<Integer,Client> c: allClients) {
            if(c.first == id)
                return c.second;
        }
        return new Client();
    }


}
