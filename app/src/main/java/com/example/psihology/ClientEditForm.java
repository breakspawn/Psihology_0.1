package com.example.psihology;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

public class ClientEditForm extends AppCompatActivity {


    private Button saveButton;
    private Button deleteButton;


    private TextInputLayout nameEdit;
    private TextInputLayout ageEdit;
    private TextInputLayout phoneEdit;
    private TextInputLayout biographyEdit;
    private TextInputLayout queryEdit;
    private TextInputLayout noteEdit;
    private TextInputLayout anamnesisEdit;
    Button meetingButton;


    private SlidrInterface sliderBack;


    private int id = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);
        Slidr.attach(this);

        initView();
        setButtonsListener();
    }

    void setButtonsListener()
    {
        Intent intent = getIntent();
        String clientJson = intent.getStringExtra("json");
        id = intent.getIntExtra("id", -1);
        // если нет переданного клиента - режим добавления нового клиента
        if(clientJson == null) {
            deleteButton.setVisibility(View.INVISIBLE);
            meetingButton.setVisibility(View.INVISIBLE);
            saveButton.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DataBaseWorker worker = new DataBaseWorker(ClientEditForm.this);
                            if (worker.insert(worker.T_CLIENTS, worker.F_DATA_CLIENT, makeClientFromFields().toJson()))
                            {
                                setFields(null);
                                Toast.makeText(ClientEditForm.this, "Сохранено", Toast.LENGTH_LONG).show();
                            }
                            else
                                Toast.makeText(ClientEditForm.this, "Ошибка добавления", Toast.LENGTH_LONG).show();
                        }

                    }
            );
        }
        // если есть переданный клиент - режим редактирования
        else
        {
            Client selectedClient = new Client(clientJson);
            // заполнить поля выбранным клиентом
            setFields(selectedClient);
            // настроить кнопку на редактирование существующего клиента
            deleteButton.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DataBaseWorker worker = new DataBaseWorker(ClientEditForm.this);
                            if (id >= 0 && worker.delete(worker.T_CLIENTS, id)) {
                                Toast.makeText(ClientEditForm.this, "Удалено", Toast.LENGTH_LONG).show();
                                finish();
                            }
                            else
                                Toast.makeText(ClientEditForm.this, "Ошибка удаления", Toast.LENGTH_LONG).show();
                        }
                    }
            );

            saveButton.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DataBaseWorker worker = new DataBaseWorker(ClientEditForm.this);

                            if (id >= 0 && worker.update(worker.T_CLIENTS, worker.F_DATA_CLIENT, makeClientFromFields().toJson(), id))
                            {
                                Toast.makeText(ClientEditForm.this, "Сохранено", Toast.LENGTH_LONG).show();
                                finish();
                            }

                            else
                                Toast.makeText(ClientEditForm.this, "Ошибка сохранения", Toast.LENGTH_LONG).show();

                        }
                    }
            );

            meetingButton.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent("com.example.psihology.MeetingEditForm");
                            intent.putExtra("id", id);
                            startActivity(intent);
                            finish();
                        }
                    }
            );
        }
    }
     /// инициализация форм
    void initView()
    {
        saveButton = (Button) findViewById(R.id.saveButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);
        nameEdit = (TextInputLayout) findViewById(R.id.editTextName);
        ageEdit = (TextInputLayout) findViewById(R.id.editTextAge);
        phoneEdit = (TextInputLayout) findViewById(R.id.editTextTelephone);
        biographyEdit = (TextInputLayout) findViewById(R.id.editTextBiography);
        queryEdit = (TextInputLayout) findViewById(R.id.editTextQuestion);
        noteEdit = (TextInputLayout) findViewById(R.id.editTextNote);
        anamnesisEdit = (TextInputLayout) findViewById(R.id.editTextAnamnez);
        sliderBack = Slidr.attach(this);
        meetingButton = (Button)findViewById(R.id.MeetingButton);

    }


    /// сброс полей в дефолтное состояние
    void setFields(Client c)
     {
         nameEdit.getEditText().setText(c == null? null : c.name);
         ageEdit.getEditText().setText(c == null? null : c.age);
         phoneEdit.getEditText().setText(c == null? null : c.phone);
         biographyEdit.getEditText().setText(c == null? null : c.biography);
         queryEdit.getEditText().setText(c == null? null : c.question);
         noteEdit.getEditText().setText(c == null? null : c.note);
         anamnesisEdit.getEditText().setText(c == null? null : c.anamnez);
     }

     /// формирует клинта на оснве заплненых полей
    Client makeClientFromFields()
    {
        Client temp = new Client();
        temp.name = String.valueOf(nameEdit.getEditText().getText());
        temp.age = String.valueOf(ageEdit.getEditText().getText());
        temp.phone = String.valueOf(phoneEdit.getEditText().getText());
        temp.biography = String.valueOf(biographyEdit.getEditText().getText());
        temp.question = String.valueOf(queryEdit.getEditText().getText());
        temp.note = String.valueOf(noteEdit.getEditText().getText());
        temp.anamnez = String.valueOf(anamnesisEdit.getEditText().getText());

        return temp;
    }
}