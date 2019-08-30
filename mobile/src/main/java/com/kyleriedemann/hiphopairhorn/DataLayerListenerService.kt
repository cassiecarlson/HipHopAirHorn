package com.kyleriedemann.hiphopairhorn

import android.content.Intent
import android.media.MediaPlayer

import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService

import timber.log.Timber

class DataLayerListenerService : WearableListenerService() {

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Timber.d("onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onMessageReceived(messageEvent: MessageEvent?) {
        Timber.d("onMessageRecieved %s", messageEvent)

        if (messageEvent!!.path == SWAG_PATH)
        //            Answers.getInstance().logCustom(new CustomEvent("Swagging out from the watch"));
            playHorn()
    }

    private fun playHorn() {
        Timber.d("playHorn")
        val airHornPlayer = MediaPlayer.create(this, R.raw.air_horn)
        airHornPlayer.setOnCompletionListener { it.release() }
        airHornPlayer.start()
    }

    companion object {

        val SWAG_PATH = "/swag"
    }
}
