package com.testeducation.ui.screen.tests.creation.input

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.widget.doOnTextChanged
import com.testeducation.logic.screen.tests.creation.question.creation.input.AnswerInputState
import com.testeducation.screen.tests.creation.question.creation.input.AnswerInputViewModel
import com.testeducation.ui.base.dialog.bottom.ViewModelHostBottomSheetDialog
import com.testeducation.ui.databinding.DialogAnswerInputBinding
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.observe

class AnswerInputDialog:
    ViewModelHostBottomSheetDialog<DialogAnswerInputBinding, AnswerInputViewModel>(
        AnswerInputViewModel::class,
        DialogAnswerInputBinding::inflate
    ) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.observe(this, ::render)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        binding {
            etAnswer.doOnTextChanged { text, start, before, count ->
                viewModel.updateAnswerText(text.toString())
            }
            btnSave.setOnClickListener {
                viewModel.save()
            }
        }
    }

    private fun render(answerInputState: AnswerInputState) = binding {
        if (etAnswer.text.isEmpty()) {
            etAnswer.setText(answerInputState.answerText)
        }
        containerEditText.backgroundTintList = ColorStateList.valueOf(answerInputState.color)
        tvMaxLength.text = answerInputState.maxLengthText
    }

}