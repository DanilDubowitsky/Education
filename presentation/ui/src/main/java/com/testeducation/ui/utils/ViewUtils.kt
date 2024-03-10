package com.testeducation.ui.utils

import android.animation.Animator
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.Animation
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup
import com.testeducation.logic.model.test.AnswerUI
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
    (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
}

fun ChipGroup.addThemes(
    themes: List<ThemeShortUI>,
    isSelectedFirst: Boolean = false,
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
            if (isSelectedFirst) check(0)
            setOnClickListener {
                onChipSelected(theme.id)
            }
            setTextColor(context.getColorStateList(R.color.selector_color_chip_text_color))
        }
        this.addView(chip)
    }
}

fun Button.switchHalfVisibleState(isVisible: Boolean) {
    alpha = if (!isVisible) {
        0.5f
    } else 1f
    isEnabled = isVisible
}

fun View.showKeyboard() {
    requestFocus()
    val inputMethodService = context.getSystemService(
        Context.INPUT_METHOD_SERVICE
    ) as InputMethodManager
    inputMethodService.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun View.hideKeyboard() {
    val inputMethodService = context.getSystemService(
        Context.INPUT_METHOD_SERVICE
    ) as InputMethodManager
    inputMethodService.hideSoftInputFromWindow(windowToken, 0)
}

fun TextView.isEllipsized(): Boolean {
    val lineCount = layout?.lineCount ?: return false
    if (lineCount == 0) return false
    for (i in 0 until lineCount) {
        if (layout.getEllipsisCount(i) > 0) return true
    }
    return false
}

var View.isFadeGone: Boolean
    get() = isGone
    set(value) {
        if (value) {
            this.animate().alpha(0f)
                .setDuration(200L)
                .withEndAction {
                    isGone = true
                }.start()
        } else {
            isGone = false
            this.animate().alpha(1f)
                .setDuration(200L)
                .start()
        }
    }

fun View.animateTranslationXAndAlpha(
    duration: Long,
    translationX: Float,
    alpha: Float,
    onEnd: (() -> Unit)? = null
) {
    this.animate().translationX(translationX)
        .alpha(alpha)
        .setListener(onEnd = onEnd)
        .start()
}

fun ViewPropertyAnimator.setListener(
    onStart: (() -> Unit)? = null,
    onEnd: (() -> Unit)? = null,
    onCancel: (() -> Unit)? = null,
    onRepeat: (() -> Unit)? = null,
): ViewPropertyAnimator {
    return this.setListener(object : Animator.AnimatorListener {

        override fun onAnimationStart(animation: Animator) {
            onStart?.invoke()
        }

        override fun onAnimationEnd(animation: Animator) {
            onEnd?.invoke()
        }

        override fun onAnimationCancel(animation: Animator) {
            onCancel?.invoke()
        }

        override fun onAnimationRepeat(animation: Animator) {
            onRepeat?.invoke()
        }
    })
}

fun Fragment.hideKeyboard() {
    view?.hideKeyboard()
}
