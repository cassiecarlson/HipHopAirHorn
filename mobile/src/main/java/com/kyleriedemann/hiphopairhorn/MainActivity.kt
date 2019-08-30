package com.kyleriedemann.hiphopairhorn

import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.ui.NavigationUI
import androidx.navigation.fragment.NavHostFragment
import timber.log.Timber


/**
 * Created by kyle
 *
 * 8/12/19
 */
class MainActivity : AppCompatActivity() {

    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        } // Else, need to wait for onRestoreInstanceState
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    private fun setupBottomNavigationBar() {
        val bottomNavView = findViewById<BottomNavigationView>(R.id.navigation)

        val navGraphIds = listOf(R.navigation.home, R.navigation.map)

//        val navHostFragment = supportFragmentManager
//            .findFragmentById(R.id.nav_host_container) as NavHostFragment?
//        NavigationUI.setupWithNavController(
//            bottomNavView,
//            navHostFragment!!.navController
//        )

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = bottomNavView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )
        // Whenever the selected controller changes, setup the action bar.
//        controller.observe(this, Observer { navController ->
//            setupActionBarWithNavController(navController)
//        })
        currentNavController = controller

        Timber.w("-------View toString-------")
        Timber.i(bottomNavView.toString())
        Timber.i(bottomNavView.toString().humanReadableViewString())
        Timber.w("-------View toString-------")

        val constraintLayout = findViewById<ConstraintLayout>(R.id.container)
        Timber.w("-------View toString-------")
        Timber.i(constraintLayout.toString())
        Timber.i(constraintLayout.toString().humanReadableViewString())
        Timber.w("-------View toString-------")
    }
}

fun String.humanReadableViewString() : String {
    return parseString(this)
}

private fun parseString(input: String) : String {
    val className = input.removeRange(input.indexOfFirst { ch -> ch == '{' }, input.length)

    var subString = input.removeRange(0, input.indexOfFirst { ch -> ch == '{' })
    subString = subString.removePrefix("{")
    subString = subString.removeSuffix("}")

    val hashCode = subString.substringBefore(" ")

    var rect = subString.substringAfterLast(" ")
    val left = rect.substringBefore(",")
    val top = rect.substringAfter(",").substringBefore("-")
    val right = rect.substringBeforeLast(",").substringAfter("-")
    val bottom = rect.substringAfterLast(",")

    rect = "\"Left\": $left, \"Top\": $top, \"Right\": $right, \"Bottom\": $bottom"

    val flags = subString.subSequence(
        subString.indexOfFirst { ch -> ch == ' ' },
        subString.indexOfLast { ch -> ch == ' ' }
    ).filterNot { ch -> ch == ' ' }.trim()

    var visible = when (flags[0]) {
        'V' -> "Visible"
        'I' -> "Invisible"
        'G' -> "Gone"
        else -> "?"
    }
    visible = "\"Visible\": \"$visible\""

    val focusable = "\"Focusable\": ${flags[1] == 'F'}"

    val enabled = "\"Enabled\": ${flags[2] == 'E'}"

    val willDraw = "\"Will Draw\": ${flags[3] != 'D'}"

    val showingHorizontalScrollbars = "\"Showing Horizontal Scrollbars\": ${flags[4] == 'H'}"

    val showingVerticalScrollbars = "\"Showing Vertical Scrollbars\": ${flags[5] == 'V'}"

    val clickable = "\"Clickable\": ${flags[6] == 'C'}"

    val longClickable = "\"Long Clickable\": ${flags[7] == 'L'}"

    val contextClickable = "\"Context Clickable\": ${flags[8] == 'X'}"

    val isRootNamespace = "\"Is Root Namespace\": ${flags[9] == 'R'}"

    val focused = "\"Focused\": ${flags[10] == 'F'}"

    val selected = "\"Selected\": ${flags[11] == 'S'}"

    var pressed = when (flags[12]) {
        'p' -> "Prepressed"
        'P' -> "Pressed"
        else -> "Neither"
    }
    pressed = "\"Pressed\": \"$pressed\""

    val hovered = "\"Hovered\": ${flags[13] == 'H'}"

    val activated = "\"Activated\": ${flags[14] == 'A'}"

    val invalidated = "\"Invalidated\": ${flags[15] == 'I'}"

    val dirty = "\"Is Dirty\": ${flags[16] == 'D'}"

    val viewFlags = listOf(
        visible, focusable, enabled, willDraw,
        showingHorizontalScrollbars, showingVerticalScrollbars,
        clickable, longClickable, contextClickable, isRootNamespace,
        focused, selected, pressed, hovered, activated, invalidated, dirty
    )

    val sb = StringBuilder()
    sb.append("{")
    sb.append("\"View\": \"$className\", ")
    sb.append("\"HashCode\": \"$hashCode\", ")
    sb.append("\"Rect\": { $rect }, ")
    sb.append("\"Flags\": {")
    viewFlags.forEachIndexed { index, s ->
        run {
            sb.append(s)
            if (index != viewFlags.lastIndex) {
                sb.append(", ")
            }
        }
    }
    sb.append("}")
    sb.append("}")

    return sb.toString()
}
