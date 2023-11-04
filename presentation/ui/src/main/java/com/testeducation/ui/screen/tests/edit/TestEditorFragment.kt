package com.testeducation.ui.screen.tests.edit

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.testeducation.logic.model.question.QuestionDetailsUi
import com.testeducation.logic.screen.tests.edit.TestEditorState
import com.testeducation.screen.tests.edit.TestEditorViewModel
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentTestEditorBinding
import com.testeducation.ui.delegates.tests.answersDisplayDelegateDefault
import com.testeducation.ui.delegates.tests.footerQuestionDetailsPlusAddDelegate
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.observe
import com.testeducation.ui.utils.simpleDiffUtil

class TestEditorFragment :
    ViewModelHostFragment<TestEditorViewModel, FragmentTestEditorBinding>(
        TestEditorViewModel::class,
        FragmentTestEditorBinding::inflate
    ) {

    private val questionAdapter by lazy {
        AsyncListDifferDelegationAdapter(
            simpleDiffUtil(QuestionDetailsUi::id),
            answersDisplayDelegateDefault(viewModel::deleteQuestion, viewModel::openEditQuestion),
            footerQuestionDetailsPlusAddDelegate(viewModel::openCreateQuestion)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        binding {
            rvQuestion.apply {
                adapter = questionAdapter
                layoutManager = LinearLayoutManager(requireContext())
                itemAnimator = null
            }
            imgEdit.setOnClickListener {
                viewModel.openTestSettings()
            }
            btnCreate.setOnClickListener {
                viewModel.publish()
            }
            btnDraft.setOnClickListener {
                viewModel.draft()
            }
        }
    }

    private fun observeData() = viewModel.observe(this, ::render)

    private fun render(state: TestEditorState) = binding {
        when (state) {
            is TestEditorState.Content -> {
                questionAdapter.items = state.questionDetailsUi
                tvTitleTest.text = state.testDetails.title
                tvTypeTest.text = state.testDetails.theme.title
                val colorTest = Color.parseColor(state.testDetails.style.color)
                toolbar.backgroundTintList = ColorStateList.valueOf(colorTest)
                btnCreate.backgroundTintList = ColorStateList.valueOf(colorTest)
                requireActivity().window.statusBarColor = colorTest
                rootGroup.isVisible = !state.visibleLoadingPublish
                loadingShimmer.isVisible = false
                loadingProgress.isVisible = state.visibleLoadingPublish
            }
            is TestEditorState.NoInit -> {
                loadingShimmer.isVisible = true
                rootGroup.isVisible = false
            }
        }
    }

}