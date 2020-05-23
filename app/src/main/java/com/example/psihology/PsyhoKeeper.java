package com.example.psihology;

import android.content.Context;
import android.util.Pair;

import java.util.ArrayList;


public class PsyhoKeeper {
    private static ArrayList<Pair<Integer, Client>> allClients;
    private static ArrayList<Pair<Integer, Meeting>> allMeetings;
    private static ArrayList<Pair<Integer, Noty>> allNotyes;

    enum Sort {
        LITTLE_ENDIAN,
        BIG_ENDIAN,
    }

    public PsyhoKeeper(Context context) {
        DataBaseWorker dw = new DataBaseWorker(context);
        allClients = dw.getAllClientsFromDB();
        allMeetings = dw.getAllMeetingsFromDB();
        allNotyes = dw.getAllNotifyFromDB();
    }


    public ArrayList<Pair<Integer, Client>> getClientsWhere(Client template) {
        return null;
    }

    public ArrayList<Pair<Integer, Noty>> getNotyesWhere(Noty template) {
        return null;
    }

    public ArrayList<Pair<Integer, Meeting>> getMeetingsWhere(int template) {
        return null;
    }


    public static ArrayList<Pair<Integer, Client>> getAllClients() {
        return allClients;
    }

    public static ArrayList<Pair<Integer, Noty>> getAllNotyes() {
        return allNotyes;
    }

    public static ArrayList<Pair<Integer, Meeting>> getAllMeetings(Sort sortingClass) {
        ArrayList<Pair<Integer, Meeting>> result = allMeetings;
        boolean isSorted = false;
        while (!isSorted) {
            for (int i = 0; i < result.size(); i++) {
                if (i == result.size() - 1) {
                    isSorted = true;
                    break;
                }

                Meeting m = result.get(i).second;
                Meeting next = result.get(i + 1).second;
                boolean needSwap = false;
                if (sortingClass == Sort.LITTLE_ENDIAN && m.getDate().after(next.getDate()))
                    needSwap = true;
                else if (sortingClass == Sort.BIG_ENDIAN && m.getDate().before(next.getDate()))
                    needSwap = true;

                if (needSwap) {
                    Pair<Integer, Meeting> temp = result.get(i + 1);
                    result.set(i + 1, result.get(i));
                    result.set(i, temp);
                    break;
                }
            }
            isSorted = true;
        }

        return sortMeetingByTime(result, sortingClass);
    }

    private static ArrayList<Pair<Integer, Meeting>> sortMeetingByTime(ArrayList<Pair<Integer, Meeting>> collection, Sort sortingClass) {
        boolean isSorted = false;
        while (!isSorted) {
            for (int i = 0; i < collection.size(); i++) {
                if (i == collection.size() - 1) {
                    isSorted = true;
                    break;
                }
                Meeting m = collection.get(i).second;
                Meeting next = collection.get(i + 1).second;
                boolean needSwap = false;
                if (sortingClass == Sort.LITTLE_ENDIAN && m.getTime().after(next.getTime()) && m.getDate().equals(next.getDate()))
                    needSwap = true;
                else if (sortingClass == Sort.BIG_ENDIAN && m.getTime().before(next.getTime()) && m.getDate().equals(next.getDate()))
                    needSwap = true;

                if (needSwap) {
                    Pair<Integer, Meeting> temp = collection.get(i + 1);
                    collection.set(i + 1, collection.get(i));
                    collection.set(i, temp);
                    break;
                }
            }
            isSorted = true;
        }

        return collection;
    }

    public static Noty getNotifyById(int id) {
        for (Pair<Integer, Noty> n : allNotyes) {
            if (n.first == id)
                return n.second;
        }
        return new Noty();
    }

    public Meeting getMeetingById(int id) {
        for (Pair<Integer, Meeting> m : allMeetings) {
            if (m.first == id)
                return m.second;
            }
        return new Meeting();
    }



    public static Client getClientById(int id){
        for (Pair<Integer,Client> c: allClients) {
            if(c.first == id)
                return c.second;
        }
        return new Client();
    }



}
