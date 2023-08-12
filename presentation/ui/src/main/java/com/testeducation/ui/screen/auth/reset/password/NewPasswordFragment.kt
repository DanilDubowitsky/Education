package com.testeducation.ui.screen.auth.reset.password

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.core.view.isGone
import androidx.core.widget.addTextChangedListener
import com.testeducation.logic.screen.auth.reset.password.NewPasswordSideEffect
import com.testeducation.logic.screen.auth.reset.password.NewPasswordState
import com.testeducation.screen.auth.reset.password.NewPasswordViewModel
import com.testeducation.ui.R
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentNewPasswordBinding
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.observe
import com.testeducation.ui.utils.setClickListener
import com.testeducation.ui.utils.showMessage
import com.testeducation.ui.utils.trimmedTextOrEmpty
import com.testeducation.ui.view.custom.InputTextPlaceHolder

class NewPasswordFragment : ViewModelHostFragment<NewPasswordViewModel, FragmentNewPasswordBinding>(
    NewPasswordViewModel::class,
    FragmentNewPasswordBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        setupListeners()
    }

    private fun observeData() = viewModel.observe(this, ::render, ::onSideEffect)

    private fun render(state: NewPasswordState) = binding {
        globalProgress.isGone = !state.isLoading
        rootForm.isGone = state.isLoading
    }

    private fun onSideEffect(sideEffect: NewPasswordSideEffect) = when (sideEffect) {
        NewPasswordSideEffect.PasswordInvalid -> showInputError(
            binding.txtPasswordPlaceHolder,
            R.string.login_password_reset_password_invalid
        )

        NewPasswordSideEffect.PasswordResetSuccess -> showMessage(
            getString(R.string.login_password_reset_success_label)
        )

        NewPasswordSideEffect.PasswordsNotMatch -> showInputError(
            binding.txtRepeatedPasswordPlaceHolder,
            R.string.login_password_reset_password_not_match
        )

        NewPasswordSideEffect.RepeatedPasswordInvalid -> showInputError(
            binding.txtRepeatedPasswordPlaceHolder,
            R.string.login_password_reset_password_invalid
        )
    }

    private fun showInputError(field: InputTextPlaceHolder, @StringRes errorRes: Int) {
        field.setErrorMsg(getString(errorRes))
    }

    private fun setupListeners() = binding {
        btnResetPassword.setClickListener(viewModel::resetPassword)
        txtNewPassword.addTextChangedListener {
            viewModel.onPasswordChanged(txtNewPassword.trimmedTextOrEmpty)
        }
        txtRepeatPassword.addTextChangedListener {
            viewModel.onRepeatedPasswordChanged(txtRepeatPassword.trimmedTextOrEmpty)
        }
    }

}