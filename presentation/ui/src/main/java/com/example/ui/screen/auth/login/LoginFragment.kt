package com.example.ui.screen.auth.login

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.example.screen.auth.login.LoginState
import com.example.screen.auth.login.LoginViewModel
import com.example.ui.base.fragment.ViewModelHostFragment
import com.example.ui.databinding.FragmentLoginBinding
import com.example.ui.utils.FragmentUtils.invoke
import com.example.ui.utils.FragmentUtils.observe
import com.example.ui.utils.ViewUtils.setClickListener
import com.example.ui.utils.ViewUtils.trimmedTextOrEmpty

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
        viewModel.observe(this, ::render)
    }

    private fun render(state: LoginState) = binding {
        rootGroup.isGone = state.isLoading
        globalProgress.isGone = !state.isLoading
    }

    private fun setupListeners() = with(binding) {
        btnLogin.setClickListener(viewModel::login)
        txtRegister.setClickListener(viewModel::registration)
        txtEmail.addTextChangedListener {
            viewModel.onEmailChanged(txtEmail.trimmedTextOrEmpty)
        }
        txtPassword.addTextChangedListener {
            viewModel.onPasswordChanged(txtPassword.trimmedTextOrEmpty)
        }
    }

}
