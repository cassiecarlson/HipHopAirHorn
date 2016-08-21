package com.kyleriedemann.hiphopairhorn;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import butterknife.BindView;

public class AirHorn extends Activity {

    // media player to play the sound
    MediaPlayer airHornPlayer;

    @BindView(R.id.air_horn_button)
    View afroGuy;

    @BindView(R.id.speaker_left)
    View leftSpeaker;

    @BindView(R.id.speaker_right)
    View rightSpeaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_horn);

        // create an intent that will start the service to listen for the message from the watch
        Intent i = new Intent(this, DataLayerListenerService.class);
        this.startService(i);

        findViewById(R.id.main_view).setOnTouchListener((view, motionEvent) -> {
            playHorn();
            scaleImage();
            return false;
        });
    }

    // method to play the animation in anim/pulse.xml
    private void scaleImage() {

        Animation pulse = AnimationUtils.loadAnimation(this, R.anim.pulse);

        afroGuy.startAnimation(pulse);

        Animation smallPulse = AnimationUtils.loadAnimation(this, R.anim.small_pulse);

        leftSpeaker.startAnimation(smallPulse);
        rightSpeaker.startAnimation(smallPulse);
    }

    // binds the sound to the media player and plays
    private void playHorn() {
        airHornPlayer = MediaPlayer.create(this, R.raw.air_horn);
        airHornPlayer.setOnCompletionListener(MediaPlayer::release);
        airHornPlayer.start();
    }
}
