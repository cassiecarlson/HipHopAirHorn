package com.kyleriedemann.hiphopairhorn;

import android.content.Intent;
import android.media.MediaPlayer;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

public class DataLayerListenerService extends WearableListenerService {

    MediaPlayer airHornPlayer;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    // getting the message from the watch
    // we can check the content of the message and do different things
    // can also send a byte stream
    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        playHorn();
    }

    private void playHorn() {
        airHornPlayer = MediaPlayer.create(this, R.raw.air_horn);
        airHornPlayer.setOnCompletionListener(MediaPlayer::release);
        airHornPlayer.start();
    }
}