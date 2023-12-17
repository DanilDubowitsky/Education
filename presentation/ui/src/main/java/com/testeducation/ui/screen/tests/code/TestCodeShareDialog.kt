package com.testeducation.ui.screen.tests.code

import android.os.Bundle
import android.view.View
import com.testeducation.logic.screen.tests.code.share.TestCodeShareState
import com.testeducation.screen.tests.code.share.TestCodeShareViewModel
import com.testeducation.ui.base.dialog.bottom.ViewModelHostBottomSheetDialog
import com.testeducation.ui.databinding.DialogTestCodeShareBinding
import com.testeducation.ui.utils.observe

class TestCodeShareDialog : ViewModelHostBottomSheetDialog<DialogTestCodeShareBinding,
        TestCodeShareViewModel>(
    TestCodeShareViewModel::class,
    DialogTestCodeShareBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    private fun observeData() = viewModel.observe(this, ::render)

    private fun render(state: TestCodeShareState) {

    }

}
