package com.kyleriedemann.hiphopairhorn

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.fragment.app.Fragment
import timber.log.Timber

/**
 * Created by kyle
 *
 * 8/14/19
 */
class AnimatedPartyFragment : Fragment() {
    internal lateinit var afroGuy: View
    internal var leftSpeaker: View? = null
    internal var rightSpeaker: View? = null

    var xDiffInTouchPointAndViewTopLeftCorner: Float = -1f
    var yDiffInTouchPointAndViewTopLeftCorner: Float = -1f

    private val springForce: SpringForce by lazy(LazyThreadSafetyMode.NONE) {
        SpringForce(0f).apply {
            stiffness = SpringForce.STIFFNESS_MEDIUM
            dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
        }
    }

    private val springAnimationTranslationX: SpringAnimation by lazy(LazyThreadSafetyMode.NONE) {
        afroGuy.springAnimationOf(DynamicAnimation.TRANSLATION_X).setSpring(springForce)
    }

    private val springAnimationTranslationY: SpringAnimation by lazy(LazyThreadSafetyMode.NONE) {
        afroGuy.springAnimationOf(DynamicAnimation.TRANSLATION_Y).setSpring(springForce)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_party, container, false)

        afroGuy = view.findViewById(R.id.air_horn_button)
        leftSpeaker = view.findViewById(R.id.speaker_left)
        rightSpeaker = view.findViewById(R.id.speaker_right)

        leftSpeaker?.visibility = GONE
        rightSpeaker?.visibility = GONE

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timber.d("afroGuy location ${afroGuy.x}, ${afroGuy.y}")

        setupTouchListener()

        // Setting up a spring animation to animate the view1 and view2 translationX and translationY properties
//        val (anim1X, anim1Y) = view.findViewById<View>(R.id.air_horn_button).let { view1 ->
//            SpringAnimation(view1, DynamicAnimation.TRANSLATION_X) to
//                    SpringAnimation(view1, DynamicAnimation.TRANSLATION_Y)
//        }

        // Registering the update listener
        springAnimationTranslationX.addUpdateListener { animation, value, velocity ->
            // Overriding the method to notify view2 about the change in the view1’s property.
            Timber.d("dynamic animaiton: $animation")
            Timber.d("current: $value, velocity: $velocity")
        }

        springAnimationTranslationY.addUpdateListener { animation, value, velocity ->
            // Overriding the method to notify view2 about the change in the view1’s property.
            Timber.d("dynamic animaiton: $animation")
            Timber.d("current: $value, velocity: $velocity")
        }
    }

    private fun setupTouchListener() {

        afroGuy.setOnTouchListener { view, motionEvent ->

            Timber.d("$motionEvent")
            when(motionEvent?.action) {

                MotionEvent.ACTION_DOWN -> {
                    xDiffInTouchPointAndViewTopLeftCorner = motionEvent.rawX - view.x
                    yDiffInTouchPointAndViewTopLeftCorner = motionEvent.rawY - view.y

                    springAnimationTranslationX.cancel()
                    springAnimationTranslationY.cancel()
                }

                MotionEvent.ACTION_MOVE -> {
                    afroGuy.x = motionEvent.rawX - xDiffInTouchPointAndViewTopLeftCorner
                    afroGuy.y = motionEvent.rawY - yDiffInTouchPointAndViewTopLeftCorner
                }

                MotionEvent.ACTION_UP -> {
                    Timber.i("xDiffInTouchPointAndViewTopLeftCorner $xDiffInTouchPointAndViewTopLeftCorner yDiffInTouchPointAndViewTopLeftCorner $yDiffInTouchPointAndViewTopLeftCorner")
                    Timber.d(springAnimationTranslationX.spring.prettyString())
                    Timber.d(springAnimationTranslationY.spring.prettyString())
                    springAnimationTranslationX.start()
                    springAnimationTranslationY.start()
                }
            }

            true
        }
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
        context?.mediaPlayerFromRaw(R.raw.air_horn)?.play()
    }
}
