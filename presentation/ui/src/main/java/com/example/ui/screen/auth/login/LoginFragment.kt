package com.example.ui.screen.auth.login

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.view.isGone
import androidx.core.widget.addTextChangedListener
import com.example.logic.screen.auth.login.LoginSideEffect
import com.example.logic.screen.auth.login.LoginState
import com.example.logic.screen.auth.registration.RegistrationSideEffect
import com.example.navigation.screen.NavigationScreen
import com.example.screen.auth.login.LoginViewModel
import com.example.ui.R
import com.example.ui.base.fragment.ViewModelHostFragment
import com.example.ui.databinding.FragmentLoginBinding
import com.example.ui.utils.invoke
import com.example.ui.utils.observe
import com.example.ui.utils.setClickListener
import com.example.ui.utils.showMessage
import com.example.ui.utils.trimmedTextOrEmpty

class LoginFragment : ViewModelHostFragment<LoginViewModel, FragmentLoginBinding>(
    LoginViewModel::class,
    FragmentLoginBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        setupListeners()
    }

    private fun observeData() {
        viewModel.observe(this, ::render, ::onSideEffect)
    }

    private fun render(state: LoginState) = binding {
        rootGroup.isGone = state.isLoading
        globalProgress.isGone = !state.isLoading
    }

    private fun onSideEffect(sideEffect: LoginSideEffect) {
        if (sideEffect is LoginSideEffect.ErrorTextInput) {
            sideEffect.mapError.forEach {
                binding.txtEmail.setErrorMsgByTag(it.key, it.value)
                binding.txtPassword.setErrorMsgByTag(it.key, it.value)
            }
        }
        if (sideEffect is LoginSideEffect.DisableTextInput) {
            sideEffect.mapDisabled.forEach {
                binding.txtEmail.setEnabledByTag(it.key, it.value)
            }
        }
    }

    private fun setupListeners() = with(binding) {
        btnLogin.setClickListener(viewModel::login)
        txtRegister.setClickListener(viewModel::registration)
        txtEmail.addTextChangedListener(viewModel::onEmailChanged)
        txtPassword.addTextChangedListener(viewModel::onPasswordChanged)
    }

}
