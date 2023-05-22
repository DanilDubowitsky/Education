package com.example.ui.utils

import android.content.res.Resources

/**
 * Convert the value from dp to px.
 */
val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()