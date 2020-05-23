package com.example.psihology;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Handler;
import android.telephony.SmsManager;
import androidx.annotation.Nullable;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;



public class SMSService extends IntentService {
    private NotificationManager notificationManager;
    public static final int DEFAULT_NOTIFICATION_ID = 101;
    String number;
    String text;
    Date date;


    public  SMSService() {
        super("");
    }
    public SMSService(String name) {
        super(name);
    }

    private Handler handler;
    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {

        handler = new Handler();


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable final Intent intent) {

        handler.post(new Runnable() {
            @Override
            public void run() {
                number = intent.getStringExtra("number");
                text = intent.getStringExtra("text");
                date = new Date(intent.getLongExtra("date", 2000));
                final Timer myTimer = new Timer(true);
                final TimerTask myTask = new TimerTask() {
                    public void run() {
                        SmsManager sms = SmsManager.getDefault();
                        sms.sendTextMessage(number, null, text,
                                null, null);
                    }
                };
                myTimer.schedule(myTask, date);
            }
        });

    }

    private void doTask()
    {}
}