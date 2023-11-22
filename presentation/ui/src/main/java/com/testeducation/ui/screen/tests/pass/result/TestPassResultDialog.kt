package com.testeducation.ui.screen.tests.pass.result

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.testeducation.logic.screen.tests.pass.result.TestPassResultState
import com.testeducation.screen.tests.pass.result.TestPassResultViewModel
import com.testeducation.ui.R
import com.testeducation.ui.base.dialog.alert.ViewModelHostAlertDialog
import com.testeducation.ui.databinding.DialogTestPassResultBinding
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.observe
import com.testeducation.ui.utils.setClickListener

class TestPassResultDialog :
    ViewModelHostAlertDialog<DialogTestPassResultBinding, TestPassResultViewModel>(
        TestPassResultViewModel::class,
        DialogTestPassResultBinding::inflate
    ) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false
        observeData()
        setupListeners()
    }

    private fun setupListeners() = binding {
        txtShopFullStatistic.setClickListener(viewModel::openFullStatistic)
        btnReturnToMainPage.setClickListener(viewModel::returnToMainPage)
    }

    private fun observeData() = viewModel.observe(this, ::render)

    private fun render(state: TestPassResultState) = binding {
        if (!state.isSuccess) {
            txtTitle.text = getString(R.string.test_pass_test_failed_title)
            imgIndicator.setImageResource(R.drawable.ic_negative_pass)
            txtDescription.text = getString(R.string.test_pass_test_failed_description)
        }
        txtTrueAnswersCount.text = state.trueAnswersCount.toString()
        txtFalseAnswersCount.text = state.falseAnswersCount.toString()
        txtTrueAnswers.text = getString(R.string.test_pass_test_correct_answers)
        txtFalseAnswers.text = getString(R.string.test_pass_test_incorrect_answers)
    }

}
