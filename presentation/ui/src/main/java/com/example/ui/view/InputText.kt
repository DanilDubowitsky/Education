package com.example.ui.view

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.AlphaAnimation
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.models.InputState
import com.example.ui.R
import com.example.ui.databinding.WidgetInputTextBinding
import com.example.ui.utils.FragmentUtils.invoke
import com.example.ui.utils.IntUtils.dp

class InputText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = WidgetInputTextBinding.inflate(LayoutInflater.from(context), this, true)

    private var _currentState: InputState = InputState.Default
    val currentState: InputState = _currentState

    private val textDefault = ContextCompat.getColor(context, R.color.colorTextPrimary)
    private val textDisabled = ContextCompat.getColor(context, R.color.colorGrayBlueDisabled)

    private var _hint: String = ""
        set(value) {
            field = value
            binding.tvLabel.text = value
            binding.etInput.hint = value
        }
    val hint: String = _hint


    private val editTextUpValueAnimator by lazy {

    }

    private val editTextDownValueAnimation by lazy {
        ValueAnimator.ofInt(
            binding.etInput.paddingTop,
            26.dp
        ).apply {
            duration = 100
            addUpdateListener { valueAnimator ->
                binding {
                    etInput.setPadding(
                        etInput.paddingStart,
                        valueAnimator.animatedValue.toString().toInt(),
                        etInput.paddingEnd,
                        etInput.paddingBottom
                    )
                }
            }
        }
    }

    init {
        context.obtainStyledAttributes(attrs, R.styleable.InputText).apply {
            _hint = getString(R.styleable.InputText_hint).orEmpty()
            recycle()
        }
        processFocusedChange()
    }

    fun setInputState(state: InputState) {
        if (currentState == state) return

        _currentState = state

        binding {
            when (state) {
                is InputState.Default -> {
                    etInput.setTextColor(textDefault)
                    etInput.isEnabled = true
                }

                is InputState.Disabled -> {
                    etInput.setTextColor(textDisabled)
                    etInput.isEnabled = false
                }

                is InputState.Error -> {
                    etInput.isEnabled = true
                }
            }
        }
    }

    private fun processFocusedChange() {
        binding {
            etInput.setOnFocusChangeListener { v, hasFocus ->
                changeFaceTextInput(hasFocus = hasFocus)
                startAnimation(hasFocus = hasFocus)
            }
        }
    }

    private fun changeFaceTextInput(hasFocus: Boolean) {
        binding {
            etInput.hint = if (hasFocus) "" else hint
            tvLabel.isVisible = hasFocus
        }
    }

    private fun startAnimation(hasFocus: Boolean) {
        if (hasFocus) {
            val alphaAnimationVisible = AlphaAnimation(0f, 1f).apply {
                duration = 100
            }
            alphaAnimationVisible.start()
            editTextDownValueAnimation.start()
        }
    }

}