package com.testeducation.ui.screen.tests.creation

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.testeducation.logic.model.test.QuestionTypeUiItem
import com.testeducation.logic.screen.tests.creation.question.SelectionQuestionTypeState
import com.testeducation.screen.tests.creation.question.SelectionQuestionTypeViewModel
import com.testeducation.ui.base.dialog.bottom.ViewModelHostBottomSheetDialog
import com.testeducation.ui.databinding.DialogSelectionQuestionTypeBinding
import com.testeducation.ui.delegates.tests.question.selectionQuestionTypeDelegate
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.observe
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
        viewModel.observe(this, ::render)
        binding.rvQuestionType.apply {
            adapter = questionTypeAdapter
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = null
        }
    }

    private fun render(selectionQuestionTypeState: SelectionQuestionTypeState) = binding.invoke {
        questionTypeAdapter.items = selectionQuestionTypeState.questionTypeUiItems
    }


}