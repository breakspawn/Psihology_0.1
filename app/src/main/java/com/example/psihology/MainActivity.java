package com.example.psihology;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

public class MainActivity extends AppCompatActivity {

    private Button buttonToEditForm;
    private Button buttonToClientListForm;
    private Button methodsTerapy;

    private SlidrInterface slidr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonToEditForm = (Button) findViewById(R.id.addAnketBt);
        buttonToClientListForm = (Button) findViewById(R.id.watchAnketBt);
        methodsTerapy = (Button) findViewById(R.id.metodsBt);

        slidr = Slidr.attach(this);

        buttonToEditForm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.example.psihology.ClientEditForm");
                        startActivity(intent);
                    }
                }
        );

        buttonToClientListForm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.example.psihology.ClientListForm");
                        startActivity(intent);
                    }
                }
        );
    }
}