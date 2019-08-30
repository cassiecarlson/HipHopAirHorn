package com.kyleriedemann.hiphopairhorn;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.Arrays;
import java.util.List;

import timber.log.Timber;

public class WearHorn extends Activity {

    public static final String SWAG_PATH = "/swag";

    private GoogleApiClient googleApiClient;

    private boolean hasSpeaker;

    View afroGuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wear_horn);

        afroGuy = findViewById(R.id.wear_button);

        hasSpeaker = hasSpeaker();

        // create the google api client to send a message from the watch to the phone
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .build();

        googleApiClient.connect();

        findViewById(R.id.wear_view).setOnTouchListener(this::onTouchListener);
    }

    private boolean onTouchListener(View view, MotionEvent motionEvent) {
        sendMessage();
        scaleImage();

        if (hasSpeaker) {
            playHorn();
        }

        return false;
    }

    // send the message to the watch
    private void sendMessage() {
        Timber.d("sendMessage");

        if (googleApiClient == null) {
            Timber.d("googleApiClient is null");
            return;
        }

        final PendingResult<NodeApi.GetConnectedNodesResult> nodes =
                Wearable.NodeApi.getConnectedNodes(googleApiClient);

        nodes.setResultCallback(this::resultCallback);
    }

    private void resultCallback(NodeApi.GetConnectedNodesResult result) {
        final List<Node> nodes1 = result.getNodes();

        Timber.d(Arrays.toString(nodes1.toArray()));

        for (int i = 0; i < nodes1.size(); i++) {
            final Node node = nodes1.get(i);

            Timber.i("Node: %s", node);

            // send the message to the watch
            // specify the api client, the node, the message content, and a byte stream array
            Wearable.MessageApi.sendMessage(googleApiClient, node.getId(), SWAG_PATH, new byte[]{1});

            Timber.d("Sent message");
        }
    }

    private void scaleImage() {
        Animation pulse = AnimationUtils.loadAnimation(this, R.anim.pulse);

        afroGuy.startAnimation(pulse);
    }

    private boolean hasSpeaker() {
        PackageManager packageManager = this.getPackageManager();
        AudioManager audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);

        // Check whether the device has a speaker.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Check FEATURE_AUDIO_OUTPUT to guard against false positives.
            if (!packageManager.hasSystemFeature(PackageManager.FEATURE_AUDIO_OUTPUT)) {
                return false;
            }

            AudioDeviceInfo[] devices = audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS);
            for (AudioDeviceInfo device : devices) {
                if (device.getType() == AudioDeviceInfo.TYPE_BUILTIN_SPEAKER) {
                    return true;
                }
            }
        }
        return false;
    }

    private void playHorn() {
        MediaPlayer airHornPlayer = MediaPlayer.create(this, R.raw.air_horn);
        airHornPlayer.setOnCompletionListener(MediaPlayer::release);
        airHornPlayer.start();
    }
}
