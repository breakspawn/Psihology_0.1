package com.example.psihology;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialCalendar;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.sql.Date;
import java.sql.Time;

public class MeetingEditForm extends AppCompatActivity {


    int id = -1;
    Client client;
    TextView textName;
    Button timeBt;
    Button dateBt;
    TextView timeText;
    TextView dateText;
    Button writeMeetingBt;

    int DIALOG_TIME = 1;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_edit_form);
        textName = (TextView)findViewById(R.id.textName);
        timeText = (TextView)findViewById(R.id.timeText);
        dateText = (TextView)findViewById(R.id.dateText);
        timeBt = (Button)findViewById(R.id.TimeBt);
        dateBt = (Button)findViewById(R.id.DateBt);
        writeMeetingBt = (Button)findViewById(R.id.writeMeeting);

        id = getIntent().getIntExtra("id", -1);
        if (id >= 0)
        {
            PsyhoKeeper psyhoKeeper = new PsyhoKeeper(MeetingEditForm.this);
            client = psyhoKeeper.getClientById(id);
            textName.setText(client.name);

            final DatePickerDialog datePickerDialog = new DatePickerDialog(MeetingEditForm.this);
            datePickerDialog.setOnDateSetListener( new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Date d = new Date(year-1900,month,dayOfMonth);
                        dateText.setText(d.toString());
                    }
                }
            );

            dateBt.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            datePickerDialog.show();
                        }
                    }
            );

                TimePickerDialog.OnTimeSetListener myCallBack = new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    Time t = new Time(hourOfDay, minute, 0);
                    String dt = t.toString().substring(0,t.toString().length()-3);
                    timeText.setText(dt);
                }
            };
            final TimePickerDialog tpd = new TimePickerDialog( this, myCallBack, 12, 0, true);

            timeBt.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tpd.show();
                        }
                    }
            );
            writeMeetingBt.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {
                            if (timeText.getText().length() != 0 && dateText.getText().length() != 0){
                            DataBaseWorker worker = new DataBaseWorker(MeetingEditForm.this);
                            ContentValues values = new ContentValues();
                            values.put(worker.F_MEETING_CLIENT_ID, id);
                            String dt = dateText.getText().toString() + " " + timeText.getText().toString();
                            values.put(worker.F_DATA_MEETING, dt);

                            if (worker.insert(worker.T_MEETINGS, values))
                            {
                                Toast.makeText(MeetingEditForm.this, "Запись добавлена", Toast.LENGTH_LONG).show();
                                finish();
                            } else
                                Toast.makeText(MeetingEditForm.this, "Ошибка записи", Toast.LENGTH_LONG).show();
                        }else Toast.makeText(MeetingEditForm.this, "Поля не заполнены", Toast.LENGTH_LONG).show();
                        }
                    }
            );

        }
        else finish();


    }
}