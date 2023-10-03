package com.testeducation.ui.screen.tests.preview

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.testeducation.logic.model.question.QuestionItemUi
import com.testeducation.logic.screen.tests.preview.TestPreviewState
import com.testeducation.screen.tests.preview.TestPreviewViewModel
import com.testeducation.ui.R
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentTestPreviewBinding
import com.testeducation.ui.delegates.tests.question.createQuestionPreviewDelegate
import com.testeducation.ui.utils.disableChangeAnimation
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.isShimmerHide
import com.testeducation.ui.utils.observe
import com.testeducation.ui.utils.setClickListener
import com.testeducation.ui.utils.simpleDiffUtil

class TestPreviewFragment : ViewModelHostFragment<TestPreviewViewModel, FragmentTestPreviewBinding>(
    TestPreviewViewModel::class,
    FragmentTestPreviewBinding::inflate
) {

    private val questionsAdapter by lazy {
        AsyncListDifferDelegationAdapter(
            simpleDiffUtil(QuestionItemUi::id),
            createQuestionPreviewDelegate()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        setupViews()
        setupListeners()
    }

    private fun setupViews() = binding {
        recyclerQuestions.adapter = questionsAdapter
        recyclerQuestions.disableChangeAnimation()
    }

    private fun observeData() = viewModel.observe(this, ::render)

    private fun render(state: TestPreviewState) = binding {
        shimmerLayout.isShimmerHide = !state.isLoading
        contentAppBar.isGone = state.isLoading
        rootScroll.isGone = state.isLoading

        recyclerQuestions.isGone = !state.isQuestionsShown
        btnShowQuestions.text = if (state.isQuestionsShown) {
            getString(R.string.test_preview_hide_questions)
        } else {
            getString(R.string.test_preview_show_questions)
        }
        questionsAdapter.items = state.questions
        renderTestDetails(state)
    }

    private fun setupListeners() = binding {
        btnShowQuestions.setClickListener(
            needDisable = false,
            viewModel::changeQuestionsVisibility
        )
    }

    private fun FragmentTestPreviewBinding.renderTestDetails(state: TestPreviewState) {
        txtTheme.text = state.theme
        txtDate.text = state.createdDate
        txtTitle.text = state.title
        txtQuestionsCount.text = resources.getQuantityString(
            R.plurals.questions_count_plurals,
            state.questions.size,
            state.questions.size
        )
        btnShowQuestions.isGone = !state.allowPreviewQuestions
        if (state.isLiked) {
            btnFavorite.setImageResource(R.drawable.ic_fav_filled)
        } else {
            btnFavorite.setImageResource(R.drawable.ic_favorite_outline)
        }
    }

}
