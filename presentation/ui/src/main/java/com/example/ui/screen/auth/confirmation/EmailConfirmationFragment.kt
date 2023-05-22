package com.example.ui.screen.auth.confirmation

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.example.logic.screen.auth.confirmation.EmailConfirmationSideEffect
import com.example.logic.screen.auth.confirmation.EmailConfirmationState
import com.example.screen.auth.confirmation.EmailConfirmationViewModel
import com.example.ui.base.fragment.ViewModelHostFragment
import com.example.ui.databinding.FragmentEmailConfirmationBinding
import com.example.ui.utils.invoke
import com.example.ui.utils.observe
import com.example.ui.utils.setClickListener
import com.example.ui.utils.trimmedTextOrEmpty

class EmailConfirmationFragment :
    ViewModelHostFragment<EmailConfirmationViewModel, FragmentEmailConfirmationBinding>(
        EmailConfirmationViewModel::class,
        FragmentEmailConfirmationBinding::inflate
    ) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    viewModel.onBackPressed()
                }
            })
        setupListeners()
        observeData()
    }

    private fun observeData() {
        viewModel.observe(this, ::render, ::onSideEffect)
    }

    private fun onSideEffect(sideEffect: EmailConfirmationSideEffect) = binding {
        when (sideEffect) {
            EmailConfirmationSideEffect.CodeValidationError -> {}
        }
    }

    private fun setupListeners() = binding {
        btnConfirmEmail.setClickListener(viewModel::confirmEmail)
        txtConfirmationCode.addTextChangedListener {
            viewModel.onCodeTextChanged(txtConfirmationCode.trimmedTextOrEmpty)
        }
    }

    private fun render(state: EmailConfirmationState) = with(binding) {
        rootGroup.isVisible = !state.isLoading
        globalProgress.isVisible = state.isLoading
    }

}