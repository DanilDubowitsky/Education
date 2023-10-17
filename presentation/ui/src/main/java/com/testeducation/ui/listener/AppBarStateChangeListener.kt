package com.testeducation.ui.listener

import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

class AppBarStateChangeListener(
    private val onStateChanged: (State) -> Unit
) : AppBarLayout.OnOffsetChangedListener {

    enum class State {
        COLLAPSED,
        EXPANDED,
        IDLE
    }

    private var currentState: State = State.IDLE

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        if (verticalOffset == 0) {
            if (currentState != State.EXPANDED) {
                onStateChanged(State.EXPANDED)
            }
            currentState = State.EXPANDED
        } else if (abs(verticalOffset) >= appBarLayout.totalScrollRange) {
            if (currentState != State.COLLAPSED) {
                onStateChanged(State.COLLAPSED)
            }
            currentState = State.COLLAPSED
        } else {
            if (currentState != State.IDLE) {
                onStateChanged(State.IDLE)
            }
            currentState = State.IDLE
        }
    }

}
