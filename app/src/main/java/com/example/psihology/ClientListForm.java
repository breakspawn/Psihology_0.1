package com.example.psihology;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.r0adkll.slidr.Slidr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClientListForm extends AppCompatActivity {

    private ListView listView;
    ArrayList<Pair<Integer, Client>> clients;
    Map<Integer, Integer> posToId;
    PsyhoKeeper keeper;

    private void updateClients()
    {
        ArrayList<String> clientNames = new ArrayList<String>();

        keeper = new PsyhoKeeper(ClientListForm.this);
        clients = keeper.getAllClients();
        clientNames.clear();
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
        Slidr.attach(this);

        updateClients();


        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                        try {
                            int idClient = posToId.get(position).intValue();
                            Client selectedClient = keeper.getClientById(idClient);
                            Intent intent = new Intent("com.example.psihology.ClientEditForm"); //это код из фрагмента
                            intent.putExtra("json", selectedClient.toJson());
                            intent.putExtra("id", idClient);
                            startActivity(intent);

                        }
                        catch (NullPointerException npe)
                        {
                            System.out.println(npe.getStackTrace());
                        }
                    }
                }
        );
    }

    @Override
    public void onResume() {
        super.onResume();
        updateClients();
    }
}