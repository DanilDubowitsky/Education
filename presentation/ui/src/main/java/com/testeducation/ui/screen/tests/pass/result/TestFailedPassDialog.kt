package com.testeducation.ui.screen.tests.pass.result

import android.os.Bundle
import android.view.View
import com.testeducation.logic.screen.tests.pass.result.failed.TestFailedPassState
import com.testeducation.screen.tests.pass.result.failed.TestFailedPassViewModel
import com.testeducation.ui.R
import com.testeducation.ui.base.dialog.alert.ViewModelHostAlertDialog
import com.testeducation.ui.databinding.DialogFailedTestResultBinding
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.observe
import com.testeducation.ui.utils.setClickListener

class TestFailedPassDialog : ViewModelHostAlertDialog<DialogFailedTestResultBinding, TestFailedPassViewModel>(
    TestFailedPassViewModel::class,
    DialogFailedTestResultBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false
        observeData()
        setupListeners()
    }

    private fun observeData() = viewModel.observe(this, ::render)

    private fun setupListeners() = binding {
        btnReturnToMainPage.setClickListener(viewModel::backToMainPaige)
    }

    private fun render(testFailedPassState: TestFailedPassState) = binding {
        if (testFailedPassState.isCheating) {
            txtTitle.text = getString(R.string.test_pass_test_failed_title)
            imgIndicator.setImageResource(R.drawable.ic_negative_pass)
            txtDescription.text = getString(R.string.test_pass_test_anti_cheating_description)
        } else {
            txtTitle.text = getString(R.string.test_pass_time_over_title)
            imgIndicator.setImageResource(R.drawable.ic_hour_glass)
            txtDescription.text = getString(R.string.test_pass_time_over_description)
        }
    }

}
