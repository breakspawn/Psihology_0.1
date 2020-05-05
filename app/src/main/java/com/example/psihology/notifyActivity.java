package com.example.psihology;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.r0adkll.slidr.Slidr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class notifyActivity extends AppCompatActivity {

    Button addNotifyButton;
    ListView notifyList;
    ArrayList<Pair<Integer, Noty>> notyes;
    Map<Integer, Integer> posToId;
    PsyhoKeeper keeper;
    int pos;


    void updateNotyList() {
        posToId = new HashMap<Integer, Integer>();
        ArrayList<String> notyfies = new ArrayList<String>();
        keeper = new PsyhoKeeper(notifyActivity.this);
        notyes = keeper.getAllNotyes();
        int i = 0;
        for (Pair<Integer, Noty> c : notyes) {
            notyfies.add(c.second.text);
            posToId.put(i++, c.first);
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.activ, notyfies); //назначение массива для тектса
        notifyList.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);

        addNotifyButton = (Button) findViewById(R.id.addNotifyButton);
        notifyList = (ListView) findViewById(R.id.listnotify);
        Slidr.attach(this);

        updateNotyList();

        notifyList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        try {
                            int idNotify = posToId.get(position).intValue();
                            Noty selectedNoty = keeper.getNotifyById(idNotify);
                            Intent intent = new Intent("com.example.psihology.editNotify"); //это код из фрагмента
                            intent.putExtra("json", selectedNoty.toJson());
                            intent.putExtra("id", idNotify);
                            startActivity(intent);
                            onResume();
                        } catch (NullPointerException npe) {
                            System.out.println(npe.getStackTrace());
                        }
                    }
                }
        );

        notifyList.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        registerForContextMenu(notifyList);
                        pos = position;


                        return false;
                    }
                }
        );
        addNotifyButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.example.psihology.editNotify");
                        startActivity(intent);
                    }
                }
        );
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteMenuButton: {
                int idFromPos = posToId.get(pos);
                DataBaseWorker dataBaseWorker = new DataBaseWorker(notifyActivity.this);
                if (pos >= 0 && dataBaseWorker.delete(dataBaseWorker.T_NOTIFY, idFromPos)) {
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

    @Override
    public void onResume() {
        super.onResume();
        updateNotyList();

    }
}
