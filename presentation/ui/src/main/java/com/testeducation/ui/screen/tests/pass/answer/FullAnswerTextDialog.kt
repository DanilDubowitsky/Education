package com.testeducation.ui.screen.tests.pass.answer

import android.os.Bundle
import android.view.View
import com.testeducation.logic.screen.tests.pass.answer.FullAnswerState
import com.testeducation.screen.tests.pass.answer.FullAnswerViewModel
import com.testeducation.ui.base.dialog.bottom.ViewModelHostBottomSheetDialog
import com.testeducation.ui.databinding.DialogFullAnswerBinding
import com.testeducation.ui.utils.observe

class FullAnswerTextDialog :
    ViewModelHostBottomSheetDialog<DialogFullAnswerBinding, FullAnswerViewModel>(
        FullAnswerViewModel::class,
        DialogFullAnswerBinding::inflate
    ) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    private fun observeData() = viewModel.observe(this, ::render)

    private fun render(state: FullAnswerState) {
        binding.etAnswer.text = state.answerText
    }

}
