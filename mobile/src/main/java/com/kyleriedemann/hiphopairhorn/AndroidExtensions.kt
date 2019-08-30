package com.kyleriedemann.hiphopairhorn

import android.content.Context
import android.media.MediaPlayer
import androidx.dynamicanimation.animation.FlingAnimation
import androidx.dynamicanimation.animation.FloatPropertyCompat
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce

/**
 * Created by kyle
 *
 * 8/13/19
 */

/**
 * Uses a [Context] to get a [MediaPlayer] from a raw resource id
 */
fun Context.mediaPlayerFromRaw(rawId: Int): MediaPlayer {
    return MediaPlayer.create(this, rawId)
}

/**
 * Creates a fire and forget completion listener that releases
 * resources when the sound is done playing
 */
fun MediaPlayer.play() {
    this.setOnCompletionListener { it.release() }
    this.start()
}

fun <K> K.flingAnimationOf(property: FloatPropertyCompat<K>): FlingAnimation {
    return FlingAnimation(this, property)
}

fun <K> K.springAnimationOf(
    property: FloatPropertyCompat<K>,
    finalPosition: Float = Float.NaN
): SpringAnimation {
    return if (finalPosition.isNaN()) {
        SpringAnimation(this, property)
    } else {
        SpringAnimation(this, property, finalPosition)
    }
}

inline fun SpringAnimation.withSpringForceProperties(func: SpringForce.() -> Unit): SpringAnimation {
    if (spring == null) {
        spring = SpringForce()
    }
    spring.func()
    return this
}

fun SpringForce.prettyString(): String {
    return "SpringForce { finalPosition: ${this.finalPosition}, dampingRatio: ${this.dampingRatio}, stiffness: ${this.stiffness} }"
}
