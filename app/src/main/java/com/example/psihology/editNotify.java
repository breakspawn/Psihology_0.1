package com.example.psihology;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class editNotify extends AppCompatActivity {


    EditText textNotify;
    Button saveNotifyButton;
    int id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_notify);

        textNotify = (EditText) findViewById(R.id.editTextNotify);
        saveNotifyButton = (Button) findViewById(R.id.saveNotifyButton);


        Intent intent = getIntent();
        String clientJson = intent.getStringExtra("json");
        id = intent.getIntExtra("id", -1);

        if(clientJson == null) {
            saveNotifyButton.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DataBaseWorker worker = new DataBaseWorker(editNotify.this);
                            if (worker.insert(worker.T_NOTIFY, worker.F_NOTIFY_TEXT, makeNotyFromField().toJson())) {
                                Toast.makeText(editNotify.this, "Сохранено", Toast.LENGTH_LONG).show();
                                finish();
                            } else
                                Toast.makeText(editNotify.this, "Ошибка сохранения", Toast.LENGTH_LONG).show();
                        }
                    }
            );
        } else {
            Noty selectedNoty = new Noty(clientJson);
            // заполнить поля выбранным клиентом
            setTextNotifyField(selectedNoty);
            saveNotifyButton.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DataBaseWorker worker = new DataBaseWorker(editNotify.this); 
                    if (id >= 0 && worker.update(worker.T_NOTIFY, worker.F_NOTIFY_TEXT, makeNotyFromField().toJson(), id))
                    {
                        Toast.makeText(editNotify.this, "Сохранено", Toast.LENGTH_LONG).show();
                        finish();
                    } else
                        Toast.makeText(editNotify.this, "Ошибка сохранения", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    void setTextNotifyField(Noty c) {
        textNotify.setText(c == null ? null : c.text);
    }

    Noty makeNotyFromField()
    {
        Noty temp = new Noty();
        temp.text = String.valueOf(textNotify.getText());
        return temp;
    }
}