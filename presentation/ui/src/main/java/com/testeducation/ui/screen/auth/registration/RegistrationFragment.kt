package com.testeducation.ui.screen.auth.registration

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.testeducation.logic.screen.auth.registration.RegistrationSideEffect
import com.testeducation.logic.screen.auth.registration.RegistrationState
import com.testeducation.screen.auth.registration.RegistrationViewModel
import com.testeducation.ui.R
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentRegistrationBinding
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.observe
import com.testeducation.ui.utils.setClickListener
import com.testeducation.ui.utils.showMessage
import com.testeducation.ui.utils.trimmedTextOrEmpty

class RegistrationFragment :
    ViewModelHostFragment<RegistrationViewModel, FragmentRegistrationBinding>(
        RegistrationViewModel::class,
        FragmentRegistrationBinding::inflate
    ) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        setupListeners()
    }

    private fun observeData() {
        viewModel.observe(this, ::render, ::onSideEffect)
    }

    private fun setupListeners() = binding {
        btnRegister.setClickListener(viewModel::register)
        txtPassword.addTextChangedListener {
            viewModel.onPasswordChanged(txtPassword.trimmedTextOrEmpty)
        }
        txtRepeatPassword.addTextChangedListener {
            viewModel.onRepeatedPasswordChanged(txtRepeatPassword.trimmedTextOrEmpty)
        }
        txtNickName.addTextChangedListener {
            viewModel.onUserNameChanged(txtNickName.trimmedTextOrEmpty)
        }
        txtEmail.addTextChangedListener {
            viewModel.onEmailChanged(txtEmail.trimmedTextOrEmpty)
        }
    }

    private fun render(state: RegistrationState) = binding {
        globalProgress.isVisible = state.isLoading
        rootForm.isVisible = !state.isLoading
    }

    private fun onSideEffect(sideEffect: RegistrationSideEffect) {
        when (sideEffect) {
            is RegistrationSideEffect.ShowMessage -> showMessage(sideEffect.message)
            is RegistrationSideEffect.ShowSuccessRegistration -> showMessage(
                getString(R.string.registration_success_message)
            )
        }
    }

}