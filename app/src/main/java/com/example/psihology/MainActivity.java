package com.example.psihology;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.VideoView;

import com.google.android.material.tabs.TabLayout;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

public class MainActivity extends AppCompatActivity {
    private Button buttonToClientListForm;
    private Button buttonToRecordListForm;
    private Button buttonToNote;
    private SlidrInterface slidr;
    VideoView videoView;
    MediaPlayer mMediaPlayer;
    int mCurrentVideoPosition;

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
        videoView = (VideoView) findViewById(R.id.videoView);

        Uri uri = Uri.parse("android.resource://"
        +getPackageName() + "/" + R.raw.video);
        videoView.setVideoURI(uri);
        videoView.start();



        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mMediaPlayer = mediaPlayer;
                // We want our video to play over and over so we set looping to true.
                mMediaPlayer.setLooping(true);
                // We then seek to the current posistion if it has been set and play the video.
                if (mCurrentVideoPosition != 0) {
                    mMediaPlayer.seekTo(mCurrentVideoPosition);
                    mMediaPlayer.start();
                }
            }
        });

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

    @Override
    protected void onPause() {
        super.onPause();
        // Capture the current video position and pause the video.
        mCurrentVideoPosition = mMediaPlayer.getCurrentPosition();
        videoView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Restart the video when resuming the Activity
        videoView.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // When the Activity is destroyed, release our MediaPlayer and set it to null.
        mMediaPlayer.release();
        mMediaPlayer = null;
    }
}