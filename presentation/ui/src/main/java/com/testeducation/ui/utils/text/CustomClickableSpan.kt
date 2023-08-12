package com.testeducation.ui.utils.text

import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View

class CustomClickableSpan(
    private val isUnderlineEnabled: Boolean,
    private val onClick: () -> Unit
) : ClickableSpan() {

    override fun onClick(widget: View) {
        onClick()
    }

    override fun updateDrawState(ds: TextPaint) {
        ds.isUnderlineText = isUnderlineEnabled
    }

}
