package com.example.psihology;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialCalendar;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.gson.Gson;

import java.sql.Date;
import java.sql.Time;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MeetingEditForm extends AppCompatActivity {


    private static final String TAG = "MeetingEditForm" ;
    int id = -1;
    Client client;
    TextView textName;
    Button timeBt;
    Button dateBt;
    TextView timeText;
    TextView dateText;
    Button writeMeetingBt;
    Time t;
    Date d;
    Date dateTimer;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_edit_form);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        textName = (TextView) findViewById(R.id.textName);
        timeText = (TextView) findViewById(R.id.timeText);
        dateText = (TextView) findViewById(R.id.dateText);
        timeBt = (Button) findViewById(R.id.TimeBt);
        dateBt = (Button) findViewById(R.id.DateBt);
        writeMeetingBt = (Button) findViewById(R.id.writeMeeting);

        id = getIntent().getIntExtra("id", -1);
        if (id >= 0) {
            PsyhoKeeper psyhoKeeper = new PsyhoKeeper(MeetingEditForm.this);
            client = psyhoKeeper.getClientById(id);
            textName.setText(client.name);

            final DatePickerDialog datePickerDialog = new DatePickerDialog(MeetingEditForm.this);
            datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                                                      @Override
                                                      public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                                                          dateTimer = new Date(year - 1900, month, dayOfMonth);

                                                          d = new Date(year - 1900, month, dayOfMonth);
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
                    t = new Time(hourOfDay, minute, 0);
                    String dt = t.toString().substring(0, t.toString().length() - 3);
                    timeText.setText(dt);
                }
            };

            final TimePickerDialog tpd = new TimePickerDialog(this, myCallBack, 12, 0, true);

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
                            if (timeText.getText().length() != 0 && dateText.getText().length() != 0) {
                                DataBaseWorker worker = new DataBaseWorker(MeetingEditForm.this);
                                ContentValues values = new ContentValues();
                                values.put(worker.F_MEETING_CLIENT_ID, id);
                                String dt = dateText.getText().toString() + " " + timeText.getText().toString();
                                values.put(worker.F_DATA_MEETING, dt);
                                if (worker.insert(worker.T_MEETINGS, values)) {
                                    smsService();
                                    Toast.makeText(MeetingEditForm.this, "Запись добавлена", Toast.LENGTH_LONG).show();
                                    finish();
                                } else
                                    Toast.makeText(MeetingEditForm.this, "Ошибка записи", Toast.LENGTH_LONG).show();
                            } else
                                Toast.makeText(MeetingEditForm.this, "Поля не заполнены", Toast.LENGTH_LONG).show();

                        }
                    }
            );

        } else finish();
    }

    // метод для запуска сервиса отправки смс за 3 часа до события
    // чтобы отправлять в то премя которое задал нужно к TD прибавить (3*3600*1000)
    void smsService()
    {
        final Intent intent = new Intent(this, SMSService.class);
        final String message = "Здраствуйте " + client.name + " у вас сегодня запись на "
                + (String) t.toString().substring(0, t.toString().length() - 3);

        // устанавлиаем дату и время
        Date date = new Date(dateTimer.getTime());
        Time time = new Time(t.getTime());
        date.setTime(date.getTime() + time.getTime());

        // перевожу дату в лонг для передачи в интент
        long TD = date.getTime();
        // запуск сервиса интент
        startService( intent.putExtra("date", TD)
                            .putExtra("text", message)
                            .putExtra("number", client.phone));
    }

}