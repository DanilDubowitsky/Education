package com.testeducation.ui.screen.tests.code

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.widget.addTextChangedListener
import com.testeducation.logic.screen.tests.code.enter.TestCodeEnterSideEffect
import com.testeducation.logic.screen.tests.code.enter.TestCodeEnterState
import com.testeducation.screen.tests.code.enter.TestCodeEnterViewModel
import com.testeducation.ui.base.dialog.bottom.ViewModelHostBottomSheetDialog
import com.testeducation.ui.databinding.DialogTestEnterCodeBinding
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.observe
import com.testeducation.ui.utils.setClickListener
import com.testeducation.ui.utils.trimmedTextOrEmpty

class TestCodeEnterDialog :
    ViewModelHostBottomSheetDialog<DialogTestEnterCodeBinding, TestCodeEnterViewModel>(
        TestCodeEnterViewModel::class,
        DialogTestEnterCodeBinding::inflate
    ) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        observeData()
    }

    private fun observeData() = viewModel.observe(this, ::render, ::onSideEffect)

    private fun onSideEffect(sideEffect: TestCodeEnterSideEffect) = binding {
        when (sideEffect) {
            is TestCodeEnterSideEffect.ShowCodeEnterError -> txtCodePlaceHolder.setErrorMsg(
                sideEffect.text
            )
        }
    }

    private fun setupListeners() = binding {
        txtCode.addTextChangedListener {
            viewModel.onCodeChange(txtCode.trimmedTextOrEmpty)
        }
        btnEnter.setClickListener(viewModel::enter)
    }

    private fun render(state: TestCodeEnterState) = binding {
        loadingIndicator.isGone = !state.isLoading
        contentGroup.isInvisible = state.isLoading
    }

}
