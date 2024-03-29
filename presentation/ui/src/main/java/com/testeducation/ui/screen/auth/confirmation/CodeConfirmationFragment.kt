package com.testeducation.ui.screen.auth.confirmation

import android.os.Bundle
import android.text.format.DateUtils.SECOND_IN_MILLIS
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.testeducation.logic.screen.auth.confirmation.CodeConfirmationSideEffect
import com.testeducation.logic.screen.auth.confirmation.CodeConfirmationState
import com.testeducation.screen.auth.confirmation.CodeConfirmationViewModel
import com.testeducation.ui.R
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentEmailConfirmationBinding
import com.testeducation.ui.helper.TimeHandler
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.observe
import com.testeducation.ui.utils.setClickListener
import com.testeducation.ui.utils.showSnackBar

class CodeConfirmationFragment :
    ViewModelHostFragment<CodeConfirmationViewModel, FragmentEmailConfirmationBinding>(
        CodeConfirmationViewModel::class,
        FragmentEmailConfirmationBinding::inflate
    ) {

    private val timeHandler by lazy {
        TimeHandler()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        observeData()
    }

    override fun onDestroyView() {
        timeHandler.release()
        super.onDestroyView()
    }

    private fun observeData() {
        viewModel.observe(this, ::render, ::onSideEffect)
    }

    private fun onSideEffect(sideEffect: CodeConfirmationSideEffect) = binding {
        when (sideEffect) {
            CodeConfirmationSideEffect.CodeValidationError -> {}
            CodeConfirmationSideEffect.RegistrationSuccess -> showSuccessRegistrationMessage()
            is CodeConfirmationSideEffect.StartTimer -> startCodeExpireTimer(sideEffect.expireTime)
        }
    }

    private fun startCodeExpireTimer(expireTime: Long) = binding {
        setRemainingTime(expireTime)
        timeHandler.setOnUpdateListener(CODE_EXPIRE_KEY, ::setRemainingTime)
        timeHandler.setOnExpireListener(CODE_EXPIRE_KEY, viewModel::onCodeExpired)
        timeHandler.start(expireTime, SECOND_IN_MILLIS, CODE_EXPIRE_KEY)
    }

    private fun setRemainingTime(remainingTime: Long) = binding {
        val time = remainingTime / SECOND_IN_MILLIS
        tvExpireTime.text = getString(
            R.string.email_confirmation_code_remaining_time, time.toString()
        )
    }

    private fun FragmentEmailConfirmationBinding.showSuccessRegistrationMessage() {
        showSnackBar(requireView(), getString(R.string.registration_success_message))
    }

    private fun setupListeners() = binding {
        codeConfirmationView.addCodeChangeListener(viewModel::onCodeChanged)
        btnConfirmEmail.setClickListener(viewModel::send)
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    viewModel.onBackPressed()
                }
            })
        tvSendCodeAgain.setClickListener(viewModel::sendCodeAgain)
    }

    private fun render(state: CodeConfirmationState) = with(binding) {
        globalProgress.isVisible = state.isLoading
        tvExpireTime.isInvisible = state.isCodeExpired
        tvSendCodeAgain.isInvisible = !state.isCodeExpired
        rootForm.isGone = state.isLoading
    }

    private companion object {
        const val CODE_EXPIRE_KEY = "CODE_EXPIRE"
    }

}