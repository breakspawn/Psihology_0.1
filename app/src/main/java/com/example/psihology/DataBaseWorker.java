package com.example.psihology;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Pair;

import java.io.IOException;
import java.util.ArrayList;

// работа с базой данных

public class DataBaseWorker
{
    private  SQLiteDatabase db;
    private DatabaseHelper helper;

    public final String T_CLIENTS = "clients";
    public final String T_MEETINGS = "meetings";
    public final String F_DATA_CLIENT = "jsonClient";
    public final String F_DATA_MEETING = "jsonMeeting";



    public DataBaseWorker(Context context)
    {
        helper = new DatabaseHelper(context);
        UpdateDB();
        try {
            db = helper.getWritableDatabase();
        } catch (SQLException e) {
            throw e;
        }
    }

    private void UpdateDB() throws Error
    {
        try {
            helper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }
    }

    public ArrayList<Pair<Integer, Meeting>> getAllMeetingsFromDB()
    {
        ArrayList<Pair<Integer, Meeting>> meetings = new ArrayList<Pair<Integer, Meeting>>();
        Cursor c = db.rawQuery("SELECT " + F_DATA_MEETING + " FROM " + T_MEETINGS, null);
        c.moveToFirst();
        while (!c.isAfterLast())
        {
            Meeting meeting = new Meeting(c.getString(c.getColumnIndex(F_DATA_MEETING)));
            meetings.add(new Pair<Integer, Meeting>(c.getInt(0), meeting));
            c.moveToNext();
        }
        c.close();

        return meetings;
    }

    public ArrayList<Pair<Integer, Client>> getAllClientsFromDB()
    {
        ArrayList<Pair<Integer,Client>> clients = new ArrayList<Pair<Integer, Client>>();
        Cursor c = db.rawQuery("SELECT * FROM "+ T_CLIENTS, null);
        c.moveToFirst();
        while(!c.isAfterLast())
        {
            Client client = new Client(c.getString(c.getColumnIndex(F_DATA_CLIENT)));
            int id = c.getInt(0);
            clients.add(new Pair<Integer, Client>(id,client));
            c.moveToNext();
        }
        c.close();

        return clients;
    }

    public boolean update (String table, String field, String write, int id)
    {
        ContentValues values = new ContentValues();
        values.put(field, write);
        return  db.update(table, values, "id = ? ", new String[]{String.valueOf(id)}) > -1;
    }

    public boolean delete (String table, int id)
    {
       return db.delete(table, "id = ?", new String[]{String.valueOf(id)}) > -1;
    }


    public boolean insert (String table, String field, String write)
    {
        ContentValues values = new ContentValues();
        values.put(field, write);
        return db.insert(table,null ,values) > -1;
    }
}
