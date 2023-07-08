package com.testeducation.ui.utils

import android.content.res.ColorStateList
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup
import com.testeducation.logic.model.theme.ThemeShortUI
import com.testeducation.ui.R


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

fun View.hideView() {
    visibility = View.GONE
}

fun View.showView() {
    visibility = View.VISIBLE
}

fun View.startAnimation(
    animation: Animation,
    onAnimationStart: (() -> Unit)? = null,
    onAnimationRepeat: (() -> Unit)? = null,
    onAnimationEnd: (() -> Unit)? = null
) {
    animation.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation?) {
            onAnimationStart?.invoke()
        }

        override fun onAnimationEnd(animation: Animation?) {
            onAnimationEnd?.invoke()
        }

        override fun onAnimationRepeat(animation: Animation?) {
            onAnimationRepeat?.invoke()
        }
    })
    this.startAnimation(animation)
}

fun RecyclerView.disableChangeAnimation() {
    (itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = false
}

fun ChipGroup.addThemes(
    themes: List<ThemeShortUI>,
    onChipSelected: (id: String) -> Unit
) {
    themes.forEachIndexed { index, theme ->
        val chipDrawableS =
            ChipDrawable.createFromAttributes(this.context, null, 0, R.style.ChipStyle)
        val chip = Chip(
            this.context,
        ).apply {
            id = index
            setChipDrawable(chipDrawableS)
            text = theme.title
            setOnClickListener {
                onChipSelected(theme.id)
            }
            setTextColor(context.getColorStateList(R.color.selector_color_chip_text_color))
        }
        this.addView(chip)
    }
}

fun Button.switchHalfVisibleState(isVisible: Boolean) {
    alpha = if (!isVisible){
        0.5f
    }
    else 1f
    isEnabled = isVisible
}
