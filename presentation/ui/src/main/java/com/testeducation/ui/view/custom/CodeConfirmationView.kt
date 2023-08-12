package com.testeducation.ui.view.custom

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import com.testeducation.ui.R
import com.testeducation.ui.databinding.ViewCodeConfirmationBinding
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.loadDrawable
import com.testeducation.ui.utils.setClickListener
import com.testeducation.ui.utils.showKeyboard

class CodeConfirmationView(
    context: Context,
    attributeSet: AttributeSet?
) : FrameLayout(context, attributeSet) {

    private val binding = ViewCodeConfirmationBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    private val firstNumberTextView
        get() = binding.tvFirst

    private val secondNumberTextView
        get() = binding.tvSecond

    private val thirdNumberTextView
        get() = binding.tvThird

    private val fourthNumberTextView
        get() = binding.tvFourth

    init {
        setupListeners()
    }

    private var onCodeChangeListener: ((String) -> Unit)? = null

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        binding.etInput.showKeyboard()
        return true
    }

    fun addCodeChangeListener(onCodeChanged: (String) -> Unit) {
        onCodeChangeListener = onCodeChanged
    }

    private fun setupListeners() = binding {
        firstNumberTextView.setClickListener {
            etInput.showKeyboard()
        }
        secondNumberTextView.setClickListener {
            etInput.showKeyboard()
        }
        thirdNumberTextView.setClickListener {
            etInput.showKeyboard()
        }
        fourthNumberTextView.setClickListener {
            etInput.showKeyboard()
        }

        firstNumberTextView.addTextChangedListener { text ->
            handleTextChange(firstNumberTextView, text?.toString())
        }
        secondNumberTextView.addTextChangedListener { text ->
            handleTextChange(secondNumberTextView, text?.toString())
        }
        thirdNumberTextView.addTextChangedListener { text ->
            handleTextChange(thirdNumberTextView, text?.toString())
        }
        fourthNumberTextView.addTextChangedListener { text ->
            handleTextChange(fourthNumberTextView, text?.toString())
        }
        binding.etInput.addTextChangedListener { text ->
            onCodeChangeListener?.invoke(text.toString())
            text?.toString()?.let(::handleTextChanged)
        }
    }

    private fun handleTextChange(textView: TextView, text: String?) {
        val background = if (text.isNullOrEmpty()) {
            context.loadDrawable(R.drawable.background_text_empty)
        } else context.loadDrawable(R.drawable.background_text_filled)
        textView.background = background
    }

    private fun handleTextChanged(text: String) {
        when (text.length) {
            0 -> {
                firstNumberTextView.text = null
            }

            1 -> {
                firstNumberTextView.setText(text[0].toString())
                secondNumberTextView.text = null
            }

            2 -> {
                secondNumberTextView.setText(text[1].toString())
                thirdNumberTextView.text = null
            }

            3 -> {
                thirdNumberTextView.setText(text[2].toString())
                fourthNumberTextView.text = null
            }

            4 -> {
                fourthNumberTextView.setText(text[3].toString())
            }
        }
    }

}
