package com.testeducation.ui.view.custom

import android.animation.ValueAnimator
import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.EditText
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.addListener
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import androidx.core.view.isVisible
import com.testeducation.ui.R
import com.testeducation.ui.databinding.ViewInputTextPlaceHolderBinding
import com.testeducation.ui.utils.dp
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.view.custom.InputTextPlaceHolder.AnimState.Companion.isDown
import com.testeducation.ui.view.custom.InputTextPlaceHolder.AnimState.Companion.isUp
import kotlinx.parcelize.Parcelize

class InputTextPlaceHolder @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    companion object {
        private const val VALUE_DURATION_DEFAULT = 100L
        private const val VALUE_VISIBLE = 1f
        private const val VALUE_INVISIBLE = 0f
        private val LABEL_MARGIN_TOP_DEFAULT = 6.dp
        private val EDIT_TEXT_MARGIN_TOP_UP = 20.dp
        private val EDIT_TEXT_MARGIN_TOP_DOWN = 26.dp
    }

    private val binding =
        ViewInputTextPlaceHolderBinding.inflate(LayoutInflater.from(context), this, true)

    private var currentState: InputState = InputState.Default

    private lateinit var editText: EditText

    private val textDefault = ContextCompat.getColor(context, R.color.colorTextPrimary)
    private val textDisabled = ContextCompat.getColor(context, R.color.colorGrayBlueTextDisabled)

    private var animState: AnimState = AnimState.UP

    private var hint: String = ""

    private val editTextUpValueAnimator by lazy {
        ValueAnimator.ofInt(
            LABEL_MARGIN_TOP_DEFAULT,
            EDIT_TEXT_MARGIN_TOP_UP
        ).apply {
            valueAnimatorUpdateListener(AnimState.UP)
        }
    }

    private val editTextDownValueAnimation by lazy {
        ValueAnimator.ofInt(
            editText.paddingTop,
            EDIT_TEXT_MARGIN_TOP_DOWN
        ).apply {
            valueAnimatorUpdateListener(AnimState.DOWN)
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


    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (R.id.rootContainer == child?.id) {
            super.addView(child, index, params)
        } else if (child is ViewGroup) {
            child.forEach { view ->
                if (view is EditText) {
                    binding.container.addView(child, index, params)
                }
            }
        } else {
            if (child is EditText) {
                setEditText(child)
                binding.container.addView(child, index, params)
            }
        }
    }

    private fun setEditText(editText: EditText) {
        if (::editText.isInitialized) throw IllegalStateException("Edit text already added")
        this.editText = editText
        hint = editText.hint?.toString().orEmpty()
        binding.tvLabel.text = hint
        setInputState(InputState.Default)
        processFocusedChange()
        reactToFocus(true)
    }

    fun setErrorMsg(errorMsg: String) {
        setInputState(InputState.Error(errorMsg))
    }

    private fun setInputState(state: InputState) {
        if (currentState == state) return

        currentState = state

        binding {
            when (state) {
                is InputState.Default -> {
                    editText.setTextColor(textDefault)
                    setSettingsByEnabled(isEnabled = true)
                    editText.background = drawable(drawableId = R.drawable.selector_input_text)
                    tvErrorText.isVisible = false
                }

                is InputState.Disabled -> {
                    editText.setTextColor(textDisabled)
                    setSettingsByEnabled(isEnabled = false)
                    tvErrorText.isVisible = false
                }

                is InputState.Error -> {
                    setSettingsByEnabled(isEnabled = true)
                    editText.background =
                        drawable(drawableId = R.drawable.selector_input_text_error)
                    tvErrorText.text = state.errorText
                    tvErrorText.isVisible = true
                }
            }
        }
    }

    private fun drawable(@DrawableRes drawableId: Int) =
        ContextCompat.getDrawable(context, drawableId)

    private fun setSettingsByEnabled(isEnabled: Boolean) {
        editText.isEnabled = isEnabled
        isFocusableInTouchMode = isEnabled
    }

    private fun processFocusedChange() {
        binding {
            editText.setOnFocusChangeListener { _, hasFocus ->
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
            editText.hint = if (hasFocus) "" else hint
            tvLabel.isVisible = hasFocus || editText.text.isNotEmpty()
        }
    }

    private fun startAnimation(hasFocus: Boolean) {
        val animIsDownAndFocused = animState.isDown() && hasFocus
        val animIsDownAndNoFocusedAndTextIsNotEmpty = animState.isDown()
                && !hasFocus && editText.text.isNotEmpty()
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

    private fun ValueAnimator.valueAnimatorUpdateListener(animStateNew: AnimState) {
        duration = VALUE_DURATION_DEFAULT
        addUpdateListener { valueAnimator ->
            actionUpdateListener(valueAnimator)
        }
        addListener(onEnd = {
            animState = animStateNew
        })
    }

    private fun actionUpdateListener(valueAnimator: ValueAnimator) {
        binding {
            editText.setPadding(
                editText.paddingStart,
                valueAnimator.animatedValue.toString().toInt(),
                editText.paddingEnd,
                editText.paddingBottom
            )
        }
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

    enum class AnimState {
        UP, DOWN;

        companion object {
            fun AnimState.isUp() = this == UP
            fun AnimState.isDown() = this == DOWN

            fun getState(order: Int) = if (order == 0) {
                UP
            } else DOWN
        }
    }

    @Parcelize
    sealed class InputState : Parcelable {
        object Default : InputState()
        class Error(val errorText: String) : InputState()
        object Disabled : InputState()

        fun isError() = this is Error
    }

}
