package com.example.psihology;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecordListForm extends AppCompatActivity {

    Button addMeeting;
    ListView listMeetings;
    ArrayList<Pair<Integer, Meeting>> meetings;
    PsyhoKeeper keeper;
    Map<Integer, Integer> posToId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list_form);


        listMeetings = (ListView) findViewById(R.id.listRecord);
        addMeeting = (Button) findViewById(R.id.addMeting);
        updateMeetingsList();


        addMeeting.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.example.psihology.ClientListForm");
                        startActivity(intent);
                    }
                }
        );


        listMeetings.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    }
                }
        );

    }

    private void updateMeetingsList ()
    {
        keeper = new PsyhoKeeper(RecordListForm.this);
        meetings = keeper.getAllMeetings(PsyhoKeeper.Sort.LITTLE_ENDIAN);
        posToId = new HashMap<Integer, Integer>();
        ArrayList<String> meetingsToList = new ArrayList<String>();
        for (int i = 0; i < meetings.size(); i++)
        {
            meetingsToList.add(meetings.get(i).second.getClientName());
            posToId.put(i, meetings.get(i).first);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.activ2, meetingsToList);
        listMeetings.setAdapter(adapter);
    }

}