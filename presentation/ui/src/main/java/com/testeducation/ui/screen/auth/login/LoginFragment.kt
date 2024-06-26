package com.testeducation.ui.screen.auth.login

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.widget.addTextChangedListener
import com.testeducation.logic.screen.auth.login.LoginSideEffect
import com.testeducation.logic.screen.auth.login.LoginState
import com.testeducation.screen.auth.login.LoginViewModel
import com.testeducation.ui.R
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentLoginBinding
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.loadColor
import com.testeducation.ui.utils.observe
import com.testeducation.ui.utils.setClickListener
import com.testeducation.ui.utils.switchHalfVisibleState
import com.testeducation.ui.utils.trimmedTextOrEmpty

class LoginFragment : ViewModelHostFragment<LoginViewModel, FragmentLoginBinding>(
    LoginViewModel::class,
    FragmentLoginBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        setupListeners()
        setupViews()
    }

    private fun setupViews() {
        requireActivity().window.statusBarColor = requireContext().loadColor(R.color.colorBlue)
    }

    private fun observeData() {
        viewModel.observe(this, ::render, ::onSideEffect)
    }

    private fun render(state: LoginState) = binding {
        btnLogin.switchHalfVisibleState(!state.isLoading)
        txtRegister.isEnabled = !state.isLoading
        txtEmail.isEnabled = !state.isLoading
        txtPassword.isEnabled = !state.isLoading
        tvForgetPassword.isEnabled = !state.isLoading

        globalProgress.isGone = !state.isLoading
    }

    private fun onSideEffect(sideEffect: LoginSideEffect) {
        when (sideEffect) {
            is LoginSideEffect.EmailInputError -> {
                binding.txtEmailPlaceHolder.setErrorMsg(sideEffect.error)
            }

            is LoginSideEffect.PasswordInputError -> {
                binding.txtPasswordPlaceHolder.setErrorMsg(sideEffect.error)
            }
        }
    }

    private fun setupListeners() = binding {
        btnLogin.setClickListener(viewModel::login)
        txtRegister.setClickListener(viewModel::registration)
        txtEmail.addTextChangedListener {
            viewModel.onEmailChanged(txtEmail.trimmedTextOrEmpty)
        }
        txtPassword.addTextChangedListener {
            viewModel.onPasswordChanged(txtPassword.trimmedTextOrEmpty)
        }
        tvForgetPassword.setClickListener(viewModel::forgetPassword)
    }

}
