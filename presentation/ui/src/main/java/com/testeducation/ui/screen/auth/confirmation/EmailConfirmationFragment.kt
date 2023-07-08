package com.testeducation.ui.screen.auth.confirmation

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.testeducation.logic.screen.auth.confirmation.EmailConfirmationSideEffect
import com.testeducation.logic.screen.auth.confirmation.EmailConfirmationState
import com.testeducation.screen.auth.confirmation.EmailConfirmationViewModel
import com.testeducation.ui.R
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentEmailConfirmationBinding
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.observe
import com.testeducation.ui.utils.setClickListener
import com.testeducation.ui.utils.showSnackBar
import com.testeducation.ui.utils.trimmedTextOrEmpty

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
            EmailConfirmationSideEffect.RegistrationSuccess -> showSuccessRegistrationMessage()
        }
    }

    private fun FragmentEmailConfirmationBinding.showSuccessRegistrationMessage() {
        showSnackBar(requireView(), getString(R.string.registration_success_message))
    }

    private fun setupListeners() = binding {
        btnConfirmEmail.setClickListener(viewModel::confirmEmail)
        txtConfirmation.addTextChangedListener {
            viewModel.onCodeTextChanged(txtConfirmation.trimmedTextOrEmpty)
        }
    }

    private fun render(state: EmailConfirmationState) = with(binding) {
        rootGroup.isVisible = !state.isLoading
        globalProgress.isVisible = state.isLoading
    }

}