package com.kyleriedemann.hiphopairhorn

import android.app.Activity
import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils

import timber.log.Timber

class AirHorn : Activity() {

    internal lateinit var afroGuy: View
    internal var leftSpeaker: View? = null
    internal var rightSpeaker: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_air_horn)

        afroGuy = findViewById(R.id.air_horn_button)
        leftSpeaker = findViewById(R.id.speaker_left)
        rightSpeaker = findViewById(R.id.speaker_right)

        Timber.d("onCreate")

        findViewById<View>(R.id.main_view).setOnTouchListener { view, _ ->
            view.performClick()
            // Answers.getInstance().logCustom(new CustomEvent("Swagging out from the phone"));
            playHorn()
            scaleImage()
            false
        }
    }

    // method to play the animation in anim/pulse.xml
    private fun scaleImage() {

        val pulse = AnimationUtils.loadAnimation(this, R.anim.pulse)

        afroGuy.startAnimation(pulse)

        val smallPulse = AnimationUtils.loadAnimation(this, R.anim.small_pulse)

        if (leftSpeaker != null) {
            leftSpeaker!!.startAnimation(smallPulse)
        }

        if (rightSpeaker != null) {
            rightSpeaker!!.startAnimation(smallPulse)
        }
    }

    // binds the sound to the media player and plays
    private fun playHorn() {
        Timber.d("playHorn")

        val airHornPlayer = this.mediaPlayerFromRaw(R.raw.air_horn)
        airHornPlayer.play()
    }
}
