package com.testeducation.ui.screen.common

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.testeducation.logic.screen.common.confirm.ConfirmCodeState
import com.testeducation.screen.common.confirm.ConfirmCodeViewModel
import com.testeducation.ui.base.dialog.bottom.ViewModelHostBottomSheetDialog
import com.testeducation.ui.databinding.DialogConfirmCodeBinding
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.observe

class ConfirmCodeDialog :
    ViewModelHostBottomSheetDialog<DialogConfirmCodeBinding, ConfirmCodeViewModel>(
        ConfirmCodeViewModel::class,
        DialogConfirmCodeBinding::inflate
    ) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        observeData()
    }

    private fun observeData() = viewModel.observe(this, ::render)

    private fun render(state: ConfirmCodeState) = binding.invoke {
        title.text = state.title
        description.text = state.description
    }
}