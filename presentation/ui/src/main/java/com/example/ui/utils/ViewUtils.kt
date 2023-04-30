package com.example.ui.utils

import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.EditText

object ViewUtils {

    private const val CLICK_DELAY = 300L

    fun View.setClickListener(onClick: () -> Unit) {
        setClickListener(true, onClick)
    }

    fun View.setClickListener(needDisable: Boolean = true, onClick: () -> Unit) {
        this.setOnClickListener {
            if (needDisable) {
                this.isEnabled = false
                Handler(Looper.getMainLooper()).postDelayed({
                    this.isEnabled = true
                }, CLICK_DELAY)
            }
            onClick()
        }
    }

    val EditText.trimmedTextOrEmpty
        get() = text.trim().toString()

}
