package com.kyleriedemann.hiphopairhorn;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.readystatesoftware.systembartint.SystemBarTintManager;


public class AirHorn extends Activity {

    // media player to play the sound
    MediaPlayer airHornPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_horn);

        // tint the status bar in Kit Kat
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setTintColor(Color.parseColor("#DA4453"));

        // create an intent that will start the service to listen for the message from the watch
        Intent i = new Intent(this, DataLayerListenerService.class);
        this.startService(i);

        findViewById(R.id.main_view).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                playHorn();
                scaleImage();
                return false;
            }
        });
    }

    // method to play the animation in anim/pulse.xml
    private void scaleImage() {
        ImageView imageView = (ImageView) findViewById(R.id.air_horn_button);
        Animation pulse = AnimationUtils.loadAnimation(this, R.anim.pulse);
        imageView.startAnimation(pulse);


        Animation smallPulse = AnimationUtils.loadAnimation(this, R.anim.small_pulse);
        ImageView imageView1 = (ImageView) findViewById(R.id.speaker_left);
        imageView1.startAnimation(smallPulse);

        ImageView imageView2 = (ImageView) findViewById(R.id.speaker_right);
        imageView2.startAnimation(smallPulse);
    }

    // binds the sound to the media player and plays
    private void playHorn() {
        airHornPlayer = MediaPlayer.create(this, R.raw.air_horn);
        airHornPlayer.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
            }
        });
        airHornPlayer.start();
        Log.d("PLAYING THE HORN", "PLAYING THE HORN FROM THE PHONE");
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.air_horn, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    */
}
