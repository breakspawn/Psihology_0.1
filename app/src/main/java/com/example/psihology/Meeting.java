package com.example.psihology;
import java.sql.Time;
import java.util.Date;

public class Meeting {

    String dt = "";
    int id_client = -1;
    public Meeting(int t_id, Date d, Time t)
    {
        this(t_id, d.toString() + " " + t.toString());
    }

    public Meeting()
    {
    }

    public Meeting(int t_id, String t_dt)
    {
        dt = t_dt;
        id_client = t_id;
    }

    public String getDateString()
    {
        return dt;
    }

    public Date getDate()
    {
        String dateStr = dt.split(" ")[0];
        return java.sql.Date.valueOf(dateStr);
    }

    public Time getTime()
    {
        String timeStr = dt.split(" ")[1];
        timeStr += ":00";
        return Time.valueOf(timeStr);
    }

    public Client getClient()
    {
        return PsyhoKeeper.getClientById(id_client);
    }
    public String getClientName()
    {
        return PsyhoKeeper.getClientById(id_client).name + "\n" + dt;
    }
    public String getPhoneNumber() {return PsyhoKeeper.getClientById(id_client).phone;}

}