package com.example.ui.view

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AlphaAnimation
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.addListener
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import com.example.logic.model.common.InputState
import com.example.ui.R
import com.example.ui.databinding.WidgetInputTextBinding
import com.example.ui.utils.dp
import com.example.ui.utils.invoke
import com.example.ui.utils.trimmedTextOrEmpty
import com.example.ui.view.InputText.AnimState.Companion.isDown
import com.example.ui.view.InputText.AnimState.Companion.isUp
import com.example.utils.StringUtils.isNotValid

class InputText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    companion object {
        private const val VALUE_DURATION_DEFAULT = 100L
        private const val VALUE_VISIBLE = 1f
        private const val VALUE_INVISIBLE = 0f
        private val LABEL_MARGIN_TOP_DEFAULT = 6.dp
        private val EDIT_TEXT_MARGIN_TOP_UP = 19.dp
        private val EDIT_TEXT_MARGIN_TOP_DOWN = 26.dp
    }

    private val binding = WidgetInputTextBinding.inflate(LayoutInflater.from(context), this, true)

    private var _currentState: InputState = InputState.Default
    val currentState: InputState = _currentState

    private val textDefault = ContextCompat.getColor(context, R.color.colorTextPrimary)
    private val textDisabled = ContextCompat.getColor(context, R.color.colorGrayBlueTextDisabled)
    private val errorColor = ContextCompat.getColor(context, R.color.colorError_1)
    private val colorDefaultState = ContextCompat.getColor(context, R.color.colorGrayBlue)

    private var _hint: String = ""
        set(value) {
            field = value

            binding.etInput.hint = value
        }
    val hint: String get() = _hint

    private var _label: String = ""
        set(value) {
            field = value
            binding.tvLabel.text = value
        }
    val label: String get() = _label

    private var _text: String = ""
        set(value) {
            binding.etInput.setText(value)
            field = value
        }
    val text: String get() = binding.etInput.text.toString()

    private var _focused: Boolean = false
    val focused: Boolean get() = _focused


    private var animState: AnimState = AnimState.UP


    private val editTextUpValueAnimator by lazy {
        ValueAnimator.ofInt(
            LABEL_MARGIN_TOP_DEFAULT,
            EDIT_TEXT_MARGIN_TOP_UP
        ).apply {
            duration = VALUE_DURATION_DEFAULT
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
            addListener(onEnd = {
                animState = AnimState.UP
            })
        }
    }

    private val editTextDownValueAnimation by lazy {
        ValueAnimator.ofInt(
            binding.etInput.paddingTop,
            EDIT_TEXT_MARGIN_TOP_DOWN
        ).apply {
            duration = VALUE_DURATION_DEFAULT
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
            addListener(onEnd = {
                animState = AnimState.DOWN
            })
        }
    }

    private val alphaVisibleAnimation by lazy {
        AlphaAnimation(VALUE_INVISIBLE, VALUE_VISIBLE).apply {
            duration = VALUE_DURATION_DEFAULT
        }
    }

    private val alphaInvisibleAnimation by lazy {
        AlphaAnimation(VALUE_VISIBLE, VALUE_INVISIBLE).apply {
            duration = VALUE_DURATION_DEFAULT
        }
    }

    init {
        context.obtainStyledAttributes(attrs, R.styleable.InputText).apply {
            _hint = getString(R.styleable.InputText_hint).orEmpty()
            _label =
                getString(R.styleable.InputText_label).orEmpty().let(::getLabelOrHintIfNotValid)
            recycle()
        }
        binding.etInput.id = View.generateViewId()
        processFocusedChange()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        isSaveEnabled = true
    }

    override fun onSaveInstanceState(): Parcelable {
        val bundle = bundleOf(
            "state" to super.onSaveInstanceState(),
            "text" to text,
            "current_state" to currentState,
            "focused" to focused,
            "animState" to animState
        )
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        var viewStateNew = state
        state?.let { lastState ->
            if (lastState is Bundle) {
                _text = lastState.getString("text").orEmpty()
                setInputState(lastState.getParcelable("current_state") ?: InputState.Default)
                viewStateNew = lastState.getParcelable("state")
                _focused = lastState.getBoolean("focused")
                animState = lastState.getParcelable("animState") ?: AnimState.UP
            }
        }
        changeFaceTextInput(focused)
        restoreAnimation()
        super.onRestoreInstanceState(viewStateNew)
    }

    private fun restoreAnimation() {
        if (animState.isUp()) {
            editTextUpValueAnimator.apply {
                duration = 0
                start()
            }
        } else {
            editTextDownValueAnimation.apply {
                duration = 0
                start()
            }
        }
    }

    fun addTextChangedListener(onTextChange: (String) -> Unit) {
        binding.etInput.doOnTextChanged { text, _, _, _ ->
            onTextChange(text.toString().trim())
        }
    }

    /**
     * Метод вынуждает сменеить View свое состояние на Error и показать ошибку
     *  */
    fun setErrorMsg(errorMsg: String) {
        setInputState(InputState.Error(errorMsg))
    }

    /**
     * Метод меняет состояние View
     *  */
    private fun setInputState(state: InputState) {
        if (currentState == state) return

        _currentState = state

        binding {
            when (state) {
                is InputState.Default -> {
                    etInput.setTextColor(textDefault)
                    setSettingsByEnabled(isEnabled = true)
                    etInput.backgroundTintList = ColorStateList.valueOf(colorDefaultState)
                    tvErrorText.isVisible = false
                }

                is InputState.Disabled -> {
                    etInput.setTextColor(textDisabled)
                    setSettingsByEnabled(isEnabled = false)
                    tvErrorText.isVisible = false
                }

                is InputState.Error -> {
                    setSettingsByEnabled(isEnabled = true)
                    etInput.background =
                        ContextCompat.getDrawable(context, R.drawable.selector_input_text_error)
                    tvErrorText.text = state.errorText
                    tvErrorText.isVisible = true
                }
            }
        }
    }

    private fun setSettingsByEnabled(isEnabled: Boolean) {
        binding.etInput.isEnabled = isEnabled
        isFocusableInTouchMode = isEnabled
    }

    private fun processFocusedChange() {
        binding {
            etInput.setOnFocusChangeListener { _, hasFocus ->
                _focused = hasFocus
                reactToFocus(hasFocus = hasFocus)
            }
        }
    }

    private fun reactToFocus(hasFocus: Boolean) {
        changeFaceTextInput(hasFocus = hasFocus)
        startAnimation(hasFocus = hasFocus)
    }

    private fun changeFaceTextInput(hasFocus: Boolean) {
        binding {
            etInput.hint = if (hasFocus) "" else hint
            tvLabel.isVisible = hasFocus || text.isNotEmpty()
        }
    }

    private fun startAnimation(hasFocus: Boolean) {
        val animIsDownAndFocused = animState.isDown() && hasFocus
        val animIsDownAndNoFocusedAndTextIsNotEmpty = animState.isDown()
                && !hasFocus && text.isNotEmpty()
        if (animIsDownAndFocused || animIsDownAndNoFocusedAndTextIsNotEmpty) {
            return
        }
        if (hasFocus) {
            binding.tvLabel.startAnimation(alphaVisibleAnimation)
            editTextDownValueAnimation.start()
        } else {
            binding.tvLabel.startAnimation(alphaInvisibleAnimation)
            editTextUpValueAnimator.start()
        }
    }

    private fun getLabelOrHintIfNotValid(labelStyle: String) = if (labelStyle.isNotValid()) {
        _hint
    } else labelStyle


    enum class AnimState {
        UP, DOWN;

        companion object {
            fun AnimState.isUp() = this == UP
            fun AnimState.isDown() = this == DOWN
        }
    }

}