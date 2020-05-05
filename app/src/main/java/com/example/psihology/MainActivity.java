package com.example.psihology;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

public class MainActivity extends AppCompatActivity {

    private Button buttonToClientListForm;
    private Button buttonToRecordListForm;
    private Button buttonToNote;
    private SlidrInterface slidr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        slidr = Slidr.attach(this);
        initButtons();
    }

    void initButtons()
    {
        buttonToClientListForm = (Button) findViewById(R.id.watchAnketBt);
        buttonToRecordListForm = (Button) findViewById(R.id.recordListBt);
        buttonToNote = (Button) findViewById(R.id.notifyBt);



        buttonToClientListForm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startIntent("com.example.psihology.ClientListForm");
                    }
                }
        );

        buttonToRecordListForm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startIntent("com.example.psihology.RecordListForm");
                    }
                }
        );

        buttonToNote.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startIntent("com.example.psihology.notify");
                    }
                }
        );
    }

    void startIntent(String action)
    {
        Intent intent = new Intent(action);
        startActivity(intent);
    }

}