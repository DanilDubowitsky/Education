package com.example.ui.utils

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.core.BaseViewModel
import com.example.ui.utils.ContextUtils.showMessage
import kotlinx.coroutines.launch

object FragmentUtils {

    fun <MODEL_STATE : Any, UI_STATE : Any, SIDE_EFFECT : Any> BaseViewModel<MODEL_STATE,
            UI_STATE, SIDE_EFFECT>.observe(
        fragment: Fragment,
        renderState: (UI_STATE) -> Unit,
        onSideEffect: ((SIDE_EFFECT) -> Unit)? = null
    ) = with(fragment) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                this@observe.stateFlow.collect(renderState)
            }
        }
        if (onSideEffect != null) {
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    this@observe.sideEffectFlow.collect(onSideEffect)
                }
            }
        }
    }

    fun Fragment.showMessage(message: String, isLong: Boolean = true) {
        requireContext().showMessage(message, isLong)
    }

}
