package com.testeducation.ui.screen.questions

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.testeducation.logic.model.question.QuestionPreviewUI
import com.testeducation.logic.screen.questions.QuestionsPreviewState
import com.testeducation.screen.questions.QuestionsPreviewViewModel
import com.testeducation.ui.R
import com.testeducation.ui.base.dialog.bottom.ViewModelHostBottomSheetDialog
import com.testeducation.ui.databinding.DialogQuestionsPreviewBinding
import com.testeducation.ui.delegates.tests.question.createQuestionPreviewDelegate
import com.testeducation.ui.utils.disableChangeAnimation
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.isShimmerHide
import com.testeducation.ui.utils.observe
import com.testeducation.ui.utils.simpleDiffUtil

class QuestionsPreviewDialog :
    ViewModelHostBottomSheetDialog<DialogQuestionsPreviewBinding, QuestionsPreviewViewModel>(
        QuestionsPreviewViewModel::class,
        DialogQuestionsPreviewBinding::inflate
    ) {

    private val questionsAdapter by lazy {
        AsyncListDifferDelegationAdapter(
            simpleDiffUtil(QuestionPreviewUI::id),
            createQuestionPreviewDelegate()
        )
    }

    override val isFullScreen: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeData()
    }

    private fun observeData() = viewModel.observe(this, ::render)

    private fun render(state: QuestionsPreviewState) = binding {
        loadingShimmer.isShimmerHide = !state.isLoading
        txtQuestionsCount.isGone = state.isLoading
        questionsRecycler.isGone = state.isLoading
        questionsAdapter.items = state.questions
        txtQuestionsCount.text = resources.getQuantityString(
            R.plurals.questions_count_plurals,
            state.questions.size,
            state.questions.size
        )
    }

    private fun setupViews() = binding {
        questionsRecycler.adapter = questionsAdapter
        questionsRecycler.disableChangeAnimation()
    }

}