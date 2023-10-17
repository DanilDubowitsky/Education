package com.testeducation.ui.screen.common

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.KeyEvent
import androidx.annotation.RawRes
import androidx.core.view.isVisible
import com.testeducation.ui.R
import com.testeducation.ui.databinding.DialogLoaderBinding

class LoaderDialog(
    context: Context,
    style: Int = R.style.FullScreenLoaderTheme
) : Dialog(context, style) {

    private val binding = DialogLoaderBinding.inflate(layoutInflater)

    companion object {
        const val TAG = "FullScreenLoaderDialog"
    }

    init {
        setContentView(binding.root)
    }

    override fun setOnCancelListener(listener: DialogInterface.OnCancelListener?) {
        super.setOnCancelListener(listener)
        this.setOnKeyListener(null)
    }

    private fun setOnBackClickListener(block: () -> Unit) {

        this.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                this.setCancelable(true)
                this.dismiss()
                block()
            }
            true
        }
    }

    private fun setTitleText(text: String) {
        binding.tvTitle.text = text
        binding.tvTitle.isVisible = true
    }

    fun setAnimationLottie(@RawRes rawId: Int) {
        binding.animationView.setAnimation(rawId)
    }

    class Builder(val context: Context) {

        var dialog: LoaderDialog = LoaderDialog(context)

        init {
            dialog.setCancelable(false)
        }

        fun setCancelable(cancelable: Boolean): Builder {
            dialog.setCancelable(cancelable)
            return this
        }

        fun setOnBackClickListener(block: () -> Unit): Builder {
            dialog.setOnBackClickListener(block)
            return this
        }

        fun setTitleText(textTitle: String): Builder {
            dialog.setTitleText(textTitle)
            return this
        }

        fun setAnimation(@RawRes rawId: Int) : Builder {
            dialog.setAnimationLottie(rawId)
            return this
        }

        fun show(): LoaderDialog {
            dialog.show()

            return dialog
        }
    }
}