package com.example.psihology;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.datepicker.MaterialCalendar;
import com.google.android.material.datepicker.MaterialDatePicker;

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
    int myHour = 14;
    int myMinute = 35;

    int myDay;
    int myMonth;
    int myYear;



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
                        myDay = dayOfMonth;
                        myMonth = month;
                        myYear = year;
                        dateText.setText(String.valueOf(myDay) + "/"+ String.valueOf(myMonth)+ "/" + String.valueOf(myYear));
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
                myHour = hourOfDay;
                myMinute = minute;
                timeText.setText(String.valueOf(myHour) + ":" + String.valueOf(myMinute));
            }
        };
            final TimePickerDialog tpd = new TimePickerDialog( this, myCallBack, myHour, myMinute, true);

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
                        public void onClick(View v) {

                        }
                    }
            );







        }
        else finish();


    }
}