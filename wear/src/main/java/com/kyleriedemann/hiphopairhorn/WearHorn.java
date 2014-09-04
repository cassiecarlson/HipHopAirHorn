package com.kyleriedemann.hiphopairhorn;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.List;

public class WearHorn extends Activity {

    private GoogleApiClient myGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rect_activity_wear_horn);

        // create the google api client to send a message from the watch to the phone
        myGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .build();
        myGoogleApiClient.connect();

        findViewById(R.id.wear_view).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                sendMessage();
                scaleImage();
                return false;
            }
        });
    }

    // send the message to the watch
    private void sendMessage() {
        if (myGoogleApiClient == null)
            return;

        final PendingResult<NodeApi.GetConnectedNodesResult> nodes = Wearable.NodeApi.getConnectedNodes(myGoogleApiClient);
        nodes.setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
            @Override
            public void onResult(NodeApi.GetConnectedNodesResult result) {
                final List<Node> nodes = result.getNodes();
                for (int i=0; i<nodes.size(); i++) {
                    final Node node = nodes.get(i);

                    // send the message to the watch
                    // specify the api client, the node, the message content, and a byte stream array
                    Wearable.MessageApi.sendMessage(myGoogleApiClient, node.getId(), "/MESSAGE", new byte[]{1});
                    Log.d("SENDING MESSAGE", "THIS IS A MESSAGE SENT TO THE PHONE FROM THE WATCH");
                }
            }
        });
    }

    private void scaleImage() {
        ImageView imageView = (ImageView) findViewById(R.id.wear_button);
        Animation pulse = AnimationUtils.loadAnimation(this, R.anim.pulse);
        imageView.startAnimation(pulse);
    }
}
