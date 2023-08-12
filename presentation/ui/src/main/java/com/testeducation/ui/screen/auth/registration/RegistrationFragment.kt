package com.testeducation.ui.screen.auth.registration

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
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
import com.testeducation.ui.utils.loadColor
import com.testeducation.ui.utils.observe
import com.testeducation.ui.utils.setClickListener
import com.testeducation.ui.utils.showMessage
import com.testeducation.ui.utils.text.CustomClickableSpan
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
        setupViews()
    }

    private fun setupViews() = binding {
        val textString = getString(R.string.registration_rules_label)
        val spannableBuilder = SpannableStringBuilder(textString)
        val rulesText = getString(R.string.registration_rules)
        val startMovementSpan = textString.length - rulesText.length
        val clickableSpan = CustomClickableSpan(false, ::onTermsClick)
        spannableBuilder.setSpan(
            clickableSpan,
            startMovementSpan,
            textString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        val colorSpan = ForegroundColorSpan(requireContext().loadColor(R.color.colorBlue))
        spannableBuilder.setSpan(
            colorSpan,
            startMovementSpan,
            textString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        termsAndConditionsTxt.text = spannableBuilder
        termsAndConditionsTxt.movementMethod = LinkMovementMethod()
    }

    private fun onTermsClick() {
        // TODO: add terms and conditions
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