package com.testeducation.ui.screen.tests.edit.publish

import android.os.Bundle
import android.view.View
import com.testeducation.logic.screen.tests.edit.TestPublishState
import com.testeducation.screen.tests.edit.publish.TestPublishViewModel
import com.testeducation.ui.base.dialog.bottom.ViewModelHostBottomSheetDialog
import com.testeducation.ui.databinding.DialogTestPublishBinding
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.observe

class TestPublishDialog :
    ViewModelHostBottomSheetDialog<DialogTestPublishBinding, TestPublishViewModel>(
        TestPublishViewModel::class,
        DialogTestPublishBinding::inflate
    ) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        binding {
            containerDraft.setOnClickListener {
                viewModel.changeStatus(TestPublishState.StatusPublish.DRAFT)
            }
            containerPublish.setOnClickListener {
                viewModel.changeStatus(TestPublishState.StatusPublish.PUBLISH)
            }
            btnSave.setOnClickListener {
                viewModel.save()
            }
        }
    }

    private fun observeData() = viewModel.observe(this, ::render)

    private fun render(state: TestPublishState) = binding {
        rbDraft.isChecked = state.statusPublish == TestPublishState.StatusPublish.DRAFT
        rbPublish.isChecked = state.statusPublish == TestPublishState.StatusPublish.PUBLISH
    }
}