package com.example.psihology;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Meeting {

    DateKeeper dateOfMeeting;
    Client client;

    public Meeting(long millis)
    {
        dateOfMeeting = new DateKeeper(millis);
    }

    public Meeting() { }

    public Meeting(String json)
    {
        Gson gson = new Gson();
        Meeting r = gson.fromJson(json, Meeting.class);
        client = r.client;
        dateOfMeeting = r.dateOfMeeting;
    }

    public String toJson()
    {
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
    }

}

class DateKeeper
{
    long secsOfUnixTime = 0;
    public DateKeeper(long millis)
    {
        secsOfUnixTime = System.currentTimeMillis()/1000;
    }

    public String getDateString()
    {
        Date date = new Date(System.currentTimeMillis() / 1000);
        SimpleDateFormat fmt = new SimpleDateFormat("MM dd, yy HH:mm");
        String result = fmt.format(date);
        return result;
    }

    public long getSecsUnixTime()
    {
        return secsOfUnixTime;
    }
}
