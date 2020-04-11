package com.example.psihology;

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

   ListView listMeetings;
   Button addMeetingBt;
    ArrayList<Pair<Integer, Meeting>> meetings;
    PsyhoKeeper keeper;
    Map<Integer, Integer> posToId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list_form);

        addMeetingBt = (Button) findViewById(R.id.addMeetingBt);
        listMeetings = (ListView) findViewById(R.id.listRecord);
        updateMeetingsList();

        addMeetingBt.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {

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
        meetings = keeper.getAllMeetings();
        posToId = new HashMap<Integer, Integer>();
        ArrayList<String> meetingsToList = new ArrayList<String>();
        for (int i = 0; i < meetings.size(); i++)
        {
            meetingsToList.add(meetings.get(i).second.client.name);
            posToId.put(i, meetings.get(i).first);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.activ, meetingsToList);
    }

}