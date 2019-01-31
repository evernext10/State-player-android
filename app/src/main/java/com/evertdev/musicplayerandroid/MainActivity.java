package com.evertdev.musicplayerandroid;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;

import co.mobiwise.library.InteractivePlayerView;
import co.mobiwise.library.OnActionClickedListener;

public class MainActivity extends AppCompatActivity implements OnClickListener, OnActionClickedListener {

    private String url_radio= "http://192.227.116.104:4350/stream";
    private ProgressBar playSeekBar;

    private TextView tvRadioUrl;
    private FloatingActionButton buttonPlay, buttonStopPlay, buttonNext;


    private MediaPlayer player;

    InteractivePlayerView mInteractivePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeUIElements();
        initializeMediaPlayer();
    }



    private void initializeUIElements() {

        mInteractivePlayerView =  findViewById(R.id.interactivePlayerView);
        mInteractivePlayerView.setMax(100000000);
        mInteractivePlayerView.setProgress(1);
        mInteractivePlayerView.setOnActionClickedListener(this);

        playSeekBar =  findViewById(R.id.progressBar1);
        playSeekBar.setMax(100);
        playSeekBar.setVisibility(View.INVISIBLE);
        playSeekBar.setIndeterminate(true);
        buttonPlay =  findViewById(R.id.buttonPlay);
        buttonStopPlay =  findViewById(R.id.buttonStop);
        buttonPlay.setOnClickListener(this);
        buttonStopPlay.setOnClickListener(this);

        buttonNext = findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(this);

        //tvRadioUrl =  findViewById(R.id.textViewRadioUrl);
        //tvRadioUrl.setText("Emisora: "+url_radio);
    }

    public void onClick(View v) {

        if (v == buttonPlay) {
            startPlaying();
        } else if (v == buttonStopPlay) {
            stopPlaying();
        } else if(v == buttonNext){
            stopPlaying();
            url_radio = "http://19763.live.streamtheworld.com/977_HITS.mp3";
            startPlaying();
        }

    }

    private void startPlaying() {


        buttonPlay.setEnabled(false);

        if (!mInteractivePlayerView.isPlaying()) {
            mInteractivePlayerView.start();
        } else {
            mInteractivePlayerView.stop();
        }

        playSeekBar.setVisibility(View.VISIBLE);

        player.prepareAsync();

        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mp) {

                player.start();

            }
        });

    }

    private void stopPlaying() {

        if (player.isPlaying()) {
            player.stop();
            player.release();
            initializeMediaPlayer();
        }

        buttonPlay.setEnabled(true);
        buttonStopPlay.setEnabled(false);
        playSeekBar.setIndeterminate(true);
        playSeekBar.setVisibility(View.INVISIBLE);

    }

    private void initializeMediaPlayer() {
        player = new MediaPlayer();
        try {
            player.setDataSource(url_radio);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        player.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {

            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                playSeekBar.setIndeterminate(false);
                playSeekBar.setSecondaryProgress(100);
                Log.i("Buffering", "" + percent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player.isPlaying()) {
            //  player.stop();
        }
    }

    @Override
    public void onActionClicked(int id) {
        switch (id) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                break;
        }
    }
}