package com.example.ui.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat


fun Context.showMessage(message: String, isLong: Boolean = true) {
    val duration = if (isLong) Toast.LENGTH_LONG
    else Toast.LENGTH_SHORT
    Toast.makeText(this, message, duration).show()
}

fun Context.loadDrawable(@DrawableRes drawableRes: Int): Drawable? {
    return ContextCompat.getDrawable(this, drawableRes)
}

fun Context.loadColor(@ColorRes color: Int): Int {
    return ContextCompat.getColor(this, color)
}
