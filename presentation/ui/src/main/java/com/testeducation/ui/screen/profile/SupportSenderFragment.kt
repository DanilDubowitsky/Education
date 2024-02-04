package com.testeducation.ui.screen.profile

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.testeducation.logic.screen.profile.support.SupportSenderSideEffect
import com.testeducation.logic.screen.profile.support.SupportSenderState
import com.testeducation.screen.profile.support.SupportSenderViewModel
import com.testeducation.ui.R
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentSupportSenderBinding
import com.testeducation.ui.utils.hideKeyboard
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.observe
import com.testeducation.ui.utils.setClickListener

class SupportSenderFragment : ViewModelHostFragment<SupportSenderViewModel, FragmentSupportSenderBinding>(
    SupportSenderViewModel::class,
    FragmentSupportSenderBinding::inflate
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        requireActivity().window.statusBarColor = requireContext().getColor(R.color.colorWhite)
        binding {
            bug.setOnClickListener {
                viewModel.changeCategory(SupportSenderState.CategoryUi.Bug)
            }
            wish.setOnClickListener {
                viewModel.changeCategory(SupportSenderState.CategoryUi.Wish)
            }
            message.setOnClickListener {
                viewModel.changeCategory(SupportSenderState.CategoryUi.Message)
            }
            editText.addTextChangedListener { text ->
                viewModel.onTextChanged(text.toString())
            }
            btnSend.setClickListener(viewModel::send)
            back.setOnClickListener {
                clearFocusAndKeyboard()
                viewModel.back()
            }
        }
    }

    private fun observeData() = viewModel.observe(this, ::render, ::onSideEffect)

    private fun render(state: SupportSenderState) = binding {
        bug.isChecked = state.categorySelected == SupportSenderState.CategoryUi.Bug
        wish.isChecked = state.categorySelected == SupportSenderState.CategoryUi.Wish
        message.isChecked = state.categorySelected == SupportSenderState.CategoryUi.Message
        bug.isClickable = !state.isLoading
        wish.isClickable = !state.isLoading
        message.isClickable = !state.isLoading
        btnSend.isClickable = !state.isLoading
        editText.isEnabled = !state.isLoading
        btnSend.text = if (state.isLoading) {
            ""
        } else requireContext().getText(R.string.common_send)
        loadingProgress.isVisible = state.isLoading
    }

    private fun onSideEffect(sideEffect: SupportSenderSideEffect) {
        when (sideEffect) {
            SupportSenderSideEffect.ClearFocus -> {
                clearFocusAndKeyboard()
            }
        }
    }

    private fun clearFocusAndKeyboard() {
        binding.editText.clearFocus()
        view?.hideKeyboard()
        viewModel.onTextChanged("")
        binding.editText.text.clear()
    }
}