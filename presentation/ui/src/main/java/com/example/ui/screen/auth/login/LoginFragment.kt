package com.example.ui.screen.auth.login

import android.os.Bundle
import android.view.View
import android.view.View.inflate
import androidx.core.view.isGone
import com.example.logic.screen.auth.login.LoginSideEffect
import com.example.logic.screen.auth.login.LoginState
import com.example.screen.auth.login.LoginViewModel
import com.example.ui.base.fragment.ViewModelHostFragment
import com.example.ui.databinding.FragmentLoginBinding
import com.example.ui.utils.invoke
import com.example.ui.utils.observe
import com.example.ui.utils.setClickListener

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
        when (sideEffect) {
            is LoginSideEffect.EmailInputError -> {
                binding.txtEmail.setErrorMsg(sideEffect.error)
            }

            is LoginSideEffect.PasswordInputError -> {
                binding.txtPassword.setErrorMsg(sideEffect.error)
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
