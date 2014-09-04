package com.kyleriedemann.hiphopairhorn;

/**
 * Created by kylealanr on 8/2/14.
 */
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;


/**
 * Created by kylealanr on 8/1/14.
 */
public class DataLayerListenerService extends WearableListenerService {

    MediaPlayer airHornPlayer;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("SERVICE HAS STARTED", "STARTED THE SERVICE");
        return super.onStartCommand(intent, flags, startId);
    }

    // getting the message from the watch
    // we can check the content of the message and do different things
    // can also send a byte stream
    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        //super.onMessageReceived(messageEvent);
        Log.d("WE GOT THE MESSAGE", "WE HAVENT CHECKED THE MESSAGE YET");
        if("/MESSAGE".equals(messageEvent.getPath())) {
            // launch some Activity or do anything you like
            Log.d("GETTING THE MESSAGE", "THE MESSAGE MADE ITS WAY TO THE PHONE");
            playHorn();
        }
        playHorn();
        Log.d("GOT MESSAGE WITHOUT MATCH", "WE DONT NEED TO CHECK");
    }

    private void playHorn() {
        airHornPlayer = MediaPlayer.create(this, R.raw.air_horn);
        airHornPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
            }
        });
        airHornPlayer.start();
        Log.d("PLAYING THE HORN FROM THE WATCH", "THE MESSAGE MUST HAVE BEEN READ");
    }
}