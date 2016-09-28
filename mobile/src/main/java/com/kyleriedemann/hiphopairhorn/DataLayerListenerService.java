package com.kyleriedemann.hiphopairhorn;

import android.content.Intent;
import android.media.MediaPlayer;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.util.Objects;

import timber.log.Timber;

public class DataLayerListenerService extends WearableListenerService {

    public static final String SWAG_PATH = "/swag";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timber.d("onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Timber.d("onMessageRecieved %s", messageEvent);

        if (Objects.equals(messageEvent.getPath(), SWAG_PATH))
            Answers.getInstance().logCustom(new CustomEvent("Swagging out from the watch"));

            playHorn();
    }

    private void playHorn() {
        Timber.d("playHorn");
        MediaPlayer airHornPlayer = MediaPlayer.create(this, R.raw.air_horn);
        airHornPlayer.setOnCompletionListener(MediaPlayer::release);
        airHornPlayer.start();
    }
}