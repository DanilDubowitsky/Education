package com.testeducation.ui.utils

import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible
import com.facebook.shimmer.ShimmerFrameLayout


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

fun ImageButton.switchEnabledState(
    enabled: Boolean,
    @DrawableRes enableIcon: Int? = null,
    @DrawableRes disableIcon: Int? = null,
    @ColorRes enableIconTint: Int? = null,
    @ColorRes disableIconTint: Int? = null
) {
    if (enabled) switchToEnable(enableIcon, enableIconTint)
    else switchToDisable(disableIcon, disableIconTint)
}

private fun ImageButton.switchToEnable(
    @DrawableRes enableIcon: Int?,
    @ColorRes enableIconTint: Int?,
) {
    this.isEnabled = true
    if (enableIcon != null) setImageDrawable(context.loadDrawable(enableIcon))
    if (enableIconTint != null) setColorFilter(context.loadColor(enableIconTint))
}

private fun ImageButton.switchToDisable(
    @DrawableRes disableIcon: Int?,
    @ColorRes disableIconTint: Int?
) {
    this.isEnabled = false
    if (disableIcon != null) setImageDrawable(context.loadDrawable(disableIcon))
    if (disableIconTint != null) setColorFilter(context.loadColor(disableIconTint))
}

var ShimmerFrameLayout.isShimmerHide: Boolean
    get() = visibility == View.GONE
    set(value) {
        visibility = if (!value) {
            startShimmer()
            View.VISIBLE
        } else {
            stopShimmer()
            View.GONE
        }
    }