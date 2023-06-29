package com.testeducation.ui.utils

import android.content.Context
import androidx.core.content.ContextCompat

fun Int.getDrawable(context: Context) = ContextCompat.getDrawable(context, this)