package com.kyleriedemann.hiphopairhorn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import timber.log.Timber

/**
 * Created by kyle
 *
 * 8/13/19
 */
class PartyFragment : Fragment() {
    internal lateinit var afroGuy: View
    internal var leftSpeaker: View? = null
    internal var rightSpeaker: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_party, container, false)

        afroGuy = view.findViewById(R.id.air_horn_button)
        leftSpeaker = view.findViewById(R.id.speaker_left)
        rightSpeaker = view.findViewById(R.id.speaker_right)

        view.setOnClickListener {
            playHorn()
            scaleImage()
        }

        return view
    }

    // method to play the animation in anim/pulse.xml
    private fun scaleImage() {

        val pulse = AnimationUtils.loadAnimation(context, R.anim.pulse)

        afroGuy.startAnimation(pulse)

        val smallPulse = AnimationUtils.loadAnimation(context, R.anim.small_pulse)

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

        val airHornPlayer = context?.mediaPlayerFromRaw(R.raw.air_horn)
        airHornPlayer?.play()
    }
}
