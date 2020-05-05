package com.example.psihology;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
import android.widget.PopupMenu;
import android.widget.Toast;

import com.r0adkll.slidr.Slidr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClientListForm extends AppCompatActivity {

    private int id = -1;
    private ListView listView;
    ArrayList<Pair<Integer, Client>> clients;
    Map<Integer, Integer> posToId;
    PsyhoKeeper keeper;
    Button addClientBt;
    int pos;

    private void updateClients()
    {
        ArrayList<String> clientNames = new ArrayList<String>();
        keeper = new PsyhoKeeper(ClientListForm.this);
        clients = keeper.getAllClients();
        int i = 0;
        for (Pair<Integer, Client> c : clients) {
            clientNames.add(c.second.name);
            posToId.put(i++,c.first);
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<String> (this, R.layout.activ, clientNames); //назначение массива для тектса
        listView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_client);
        posToId = new HashMap<Integer, Integer>();
        listView = (ListView)findViewById(R.id.listActiv);
        addClientBt = (Button)findViewById(R.id.addClientBt);
        Slidr.attach(this);
        updateClients();
        initButtons();
    }

    void initButtons()
    {
        addClientBt.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.example.psihology.ClientEditForm");
                        startActivity(intent);
                        onResume();
                        finish();
                    }
                }
        );

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        try {
                            int idClient = posToId.get(position).intValue();
                            Client selectedClient = keeper.getClientById(idClient);
                            Intent intent = new Intent("com.example.psihology.ClientEditForm"); //это код из фрагмента
                            intent.putExtra("json", selectedClient.toJson());
                            intent.putExtra("id", idClient);
                            startActivity(intent);
                            onResume();

                        }
                        catch (NullPointerException npe)
                        {
                            System.out.println(npe.getStackTrace());
                        }
                    }
                }
        );

        listView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        registerForContextMenu(listView);
                        pos = position;
                        return false;
                    }
                }
        );
    }

    @Override
    public void onResume() {
        super.onResume();
        updateClients();
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.deleteMenuButton: {
                int idFromPos = posToId.get(pos);
                DataBaseWorker dataBaseWorker = new DataBaseWorker(ClientListForm.this);
                if(pos >= 0 && dataBaseWorker.delete(dataBaseWorker.T_CLIENTS,idFromPos))
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


}
