package com.testeducation.ui.screen.tests.creation

import android.os.Bundle
import android.view.View
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.testeducation.logic.model.test.QuestionTypeUiItem
import com.testeducation.screen.tests.creation.question.SelectionQuestionTypeViewModel
import com.testeducation.ui.base.dialog.bottom.ViewModelHostBottomSheetDialog
import com.testeducation.ui.databinding.DialogSelectionQuestionTypeBinding
import com.testeducation.ui.delegates.tests.question.selectionQuestionTypeDelegate
import com.testeducation.ui.utils.simpleDiffUtil

class SelectionQuestionTypeDialog :
    ViewModelHostBottomSheetDialog<DialogSelectionQuestionTypeBinding, SelectionQuestionTypeViewModel>(
        SelectionQuestionTypeViewModel::class,
        DialogSelectionQuestionTypeBinding::inflate
    ) {
    override val isFullScreen: Boolean
        get() = true

    private val questionTypeAdapter by lazy {
        AsyncListDifferDelegationAdapter(
            simpleDiffUtil(QuestionTypeUiItem::type),
            selectionQuestionTypeDelegate(viewModel::submit)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}