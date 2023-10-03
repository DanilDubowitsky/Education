package com.testeducation.ui.screen.tests.preview

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import com.testeducation.logic.screen.tests.preview.TestPreviewState
import com.testeducation.screen.tests.preview.TestPreviewViewModel
import com.testeducation.ui.R
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentTestPreviewBinding
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.isShimmerHide
import com.testeducation.ui.utils.observe

class TestPreviewFragment : ViewModelHostFragment<TestPreviewViewModel, FragmentTestPreviewBinding>(
    TestPreviewViewModel::class,
    FragmentTestPreviewBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    private fun observeData() = viewModel.observe(this, ::render)

    private fun render(state: TestPreviewState) = binding {
        shimmerLayout.isShimmerHide = !state.isLoading
        contentAppBar.isGone = state.isLoading
        rootScroll.isGone = state.isLoading
        renderTestDetails(state)
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
    }

}
