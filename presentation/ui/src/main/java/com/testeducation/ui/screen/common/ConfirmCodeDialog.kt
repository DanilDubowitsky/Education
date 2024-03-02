package com.testeducation.ui.screen.common

import android.os.Bundle
import android.text.format.DateUtils
import android.view.View
import android.view.WindowManager
import androidx.core.view.isVisible
import com.testeducation.logic.screen.common.confirm.ConfirmCodeState
import com.testeducation.logic.screen.common.confirm.ConfirmSideEffect
import com.testeducation.screen.common.confirm.ConfirmCodeViewModel
import com.testeducation.ui.R
import com.testeducation.ui.base.dialog.bottom.ViewModelHostBottomSheetDialog
import com.testeducation.ui.databinding.DialogConfirmCodeBinding
import com.testeducation.ui.helper.TimeHandler
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.observe
import com.testeducation.ui.utils.setClickListener

class ConfirmCodeDialog :
    ViewModelHostBottomSheetDialog<DialogConfirmCodeBinding, ConfirmCodeViewModel>(
        ConfirmCodeViewModel::class,
        DialogConfirmCodeBinding::inflate
    ) {

    private companion object {
        const val CODE_EXPIRE_KEY = "CODE_EXPIRE"
    }

    private val timeHandler by lazy {
        TimeHandler()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        observeData()
        binding.codeConfirmationView.addCodeChangeListener(viewModel::onCodeChanged)
        binding.tvSendCode.setClickListener(viewModel::sendCodeAgain)
        binding.btnConfirmEmail.setClickListener(viewModel::confirm)
    }

    override fun onDestroyView() {
        timeHandler.release()
        super.onDestroyView()
    }

    private fun observeData() = viewModel.observe(this, ::render, ::onSideEffect)

    private fun render(state: ConfirmCodeState) = binding.invoke {
        title.text = state.title.ifEmpty { getString(R.string.confirm_input_code_title) }
        description.text = state.description
        loadingIndicator.setVisibility(state.isLoading)
        setVisibilityGroup(!state.isLoading)
        tvSendCode.isVisible = state.isSendCodeRetry && !state.isLoading
        tvExpireTime.isVisible = !state.isSendCodeRetry && !state.isLoading
    }

    private fun setVisibilityGroup(isVisible: Boolean) = binding {
        title.isVisible = isVisible
        description.isVisible = isVisible
        codeConfirmationView.isVisible = isVisible
        tvExpireTime.isVisible = isVisible
        tvSendCode.isVisible = isVisible
        btnConfirmEmail.isVisible = isVisible
    }

    private fun onSideEffect(sideEffect: ConfirmSideEffect) = binding {
        when (sideEffect) {
            is ConfirmSideEffect.StartTime -> {
                startCodeExpireTimer(sideEffect.timeLeftMills)
            }
        }
    }

    private fun startCodeExpireTimer(expireTime: Long) = binding {
        setRemainingTime(expireTime)
        timeHandler.setOnUpdateListener(CODE_EXPIRE_KEY, ::setRemainingTime)
        timeHandler.setOnExpireListener(CODE_EXPIRE_KEY, viewModel::onCodeExpired)
        timeHandler.start(
            expireTime,
            DateUtils.SECOND_IN_MILLIS,
            CODE_EXPIRE_KEY
        )
    }

    private fun setRemainingTime(remainingTime: Long) = binding {
        val time = remainingTime / DateUtils.SECOND_IN_MILLIS
        tvExpireTime.text = getString(
            R.string.email_confirmation_code_remaining_time, time.toString()
        )
    }
}