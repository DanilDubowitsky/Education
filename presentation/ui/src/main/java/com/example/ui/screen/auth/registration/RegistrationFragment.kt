package com.example.ui.screen.auth.registration

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.example.logic.screen.auth.registration.RegistrationSideEffect
import com.example.logic.screen.auth.registration.RegistrationState
import com.example.screen.auth.registration.RegistrationViewModel
import com.example.ui.R
import com.example.ui.base.fragment.ViewModelHostFragment
import com.example.ui.databinding.FragmentRegistrationBinding
import com.example.ui.utils.invoke
import com.example.ui.utils.observe
import com.example.ui.utils.setClickListener
import com.example.ui.utils.showMessage
import com.example.ui.utils.trimmedTextOrEmpty

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
        btnRegister.setClickListener {
            viewModel.register(
                txtNickName.text,
                txtPassword.text,
                txtLogin.text,
                txtRepeatPassword.text
            )
        }
    }

    private fun render(state: RegistrationState) = binding {
        globalProgress.isVisible = state.isLoading
        rootContent.isVisible = !state.isLoading
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