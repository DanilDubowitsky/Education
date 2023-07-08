package com.testeducation.ui.utils

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.testeducation.core.BaseViewModel
import com.testeducation.navigation.screen.NavigationScreen
import kotlinx.coroutines.launch


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

fun Fragment.showSnackBar(view: View, message: String, isLong: Boolean = true) {
    requireContext().showSnackBar(view, message, isLong)
}

fun <T : Fragment> Fragment.withScreen(screen: NavigationScreen): T {
    arguments = Bundle().apply {
        putSerializable(screen::class.simpleName, screen)
    }
    return this as T
}

inline fun <reified T : NavigationScreen> Fragment.getScreen(): T {
    return arguments?.getSerializable(T::class.simpleName) as T
}

inline operator fun <T : ViewBinding> T.invoke(binding: T.() -> Unit) {
    binding.invoke(this)
}

inline operator fun <T : View> T.invoke(binding: T.() -> Unit) {
    binding.invoke(this)
}
