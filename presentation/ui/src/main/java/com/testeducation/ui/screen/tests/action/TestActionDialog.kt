package com.testeducation.ui.screen.tests.action

import android.os.Bundle
import android.view.View
import com.testeducation.logic.screen.tests.action.TestActionState
import com.testeducation.screen.tests.action.TestActionViewModel
import com.testeducation.ui.R
import com.testeducation.ui.base.dialog.bottom.ViewModelHostBottomSheetDialog
import com.testeducation.ui.databinding.DialogTestActionBinding
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.observe
import com.testeducation.ui.utils.setClickListener

class TestActionDialog :
    ViewModelHostBottomSheetDialog<DialogTestActionBinding, TestActionViewModel>(
        TestActionViewModel::class,
        DialogTestActionBinding::inflate
    ) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        setupListeners()
    }

    private fun observeData() = viewModel.observe(this, ::render)

    private fun setupListeners() = binding {
        btnAction.setClickListener(viewModel::onActionClick)
        btnToInformation.setClickListener(viewModel::onInfoClick)
    }

    private fun render(state: TestActionState) = binding {
        when {
            state.passed -> {
                btnAction.text = getString(R.string.test_action_to_results)
            }
            state.owner -> {
                btnAction.text = getString(R.string.test_action_to_redaction)
            }
        }
        txtTitle.text = state.testTitle
    }

}


