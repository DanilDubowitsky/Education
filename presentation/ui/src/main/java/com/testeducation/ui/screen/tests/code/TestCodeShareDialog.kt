package com.testeducation.ui.screen.tests.code

import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.testeducation.logic.screen.tests.code.share.TestCodeShareState
import com.testeducation.screen.tests.code.share.TestCodeShareViewModel
import com.testeducation.ui.R
import com.testeducation.ui.base.dialog.bottom.ViewModelHostBottomSheetDialog
import com.testeducation.ui.databinding.DialogTestCodeShareBinding
import com.testeducation.ui.utils.copyToClipboard
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.observe
import com.testeducation.ui.utils.setClickListener
import com.testeducation.ui.utils.showMessage
import com.testeducation.ui.utils.trimmedTextOrEmpty

class TestCodeShareDialog : ViewModelHostBottomSheetDialog<DialogTestCodeShareBinding,
        TestCodeShareViewModel>(
    TestCodeShareViewModel::class,
    DialogTestCodeShareBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        setupListeners()
    }

    private fun observeData() = viewModel.observe(this, ::render)

    private fun setupListeners() = binding {
        btnClose.setClickListener(viewModel::exit)
        txtCopy.setClickListener {
            requireContext().copyToClipboard(txtCode.trimmedTextOrEmpty)
            showMessage(
                getString(
                    R.string.code_share_copy_success_label,
                    txtCode.trimmedTextOrEmpty
                )
            )
        }
    }

    private fun render(state: TestCodeShareState) = binding {
        txtCode.setText(state.code)
        contentGroup.isInvisible = state.isLoading
        loadingIndicator.isVisible = state.isLoading
    }

}
