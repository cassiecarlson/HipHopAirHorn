package com.kyleriedemann.hiphopairhorn;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class AirHorn extends Activity {

    @BindView(R.id.air_horn_button)
    View afroGuy;

    @Nullable
    @BindView(R.id.speaker_left)
    View leftSpeaker;

    @Nullable
    @BindView(R.id.speaker_right)
    View rightSpeaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_horn);

        ButterKnife.bind(this);

        Timber.d("onCreate");

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

        if (leftSpeaker != null) {
            leftSpeaker.startAnimation(smallPulse);
        }

        if (rightSpeaker != null) {
            rightSpeaker.startAnimation(smallPulse);
        }
    }

    // binds the sound to the media player and plays
    private void playHorn() {
        Timber.d("playHorn");

        MediaPlayer airHornPlayer = MediaPlayer.create(this, R.raw.air_horn);
        airHornPlayer.setOnCompletionListener(MediaPlayer::release);
        airHornPlayer.start();
    }
}
