package com.example.psihology;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Pair;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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
    int pos = -1;

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
                        onResume();
                    }
                }
        );

        listMeetings.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        registerForContextMenu(listMeetings);
                        pos = position;
                        return false;
                    }
                }
        );
    }
    @Override
    public void onResume() {
        super.onResume();
        updateMeetingsList();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.deleteMenuButton: {
                int idFromPos = posToId.get(pos);
                DataBaseWorker dataBaseWorker = new DataBaseWorker(RecordListForm.this);
                if(pos >= 0 && dataBaseWorker.delete(dataBaseWorker.T_MEETINGS, idFromPos))
                {
                    Toast.makeText(this, "запись удалена", Toast.LENGTH_LONG).show();
                    onResume();
                    break;
                } else
                Toast.makeText(this, "ошибка удаления", Toast.LENGTH_LONG).show();
                break;
            }
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu, menu);
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