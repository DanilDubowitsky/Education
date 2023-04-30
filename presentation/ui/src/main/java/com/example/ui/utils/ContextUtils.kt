package com.example.ui.utils

import android.content.Context
import android.widget.Toast

object ContextUtils {

    fun Context.showMessage(message: String, isLong: Boolean = true) {
        val duration = if (isLong) Toast.LENGTH_LONG
        else Toast.LENGTH_SHORT
        Toast.makeText(this, message, duration).show()
    }

}
