package com.testeducation.ui.screen.tests.edit

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.testeducation.logic.model.question.QuestionItemUi
import com.testeducation.logic.screen.tests.edit.TestEditorState
import com.testeducation.screen.tests.edit.TestEditorViewModel
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentTestEditorBinding
import com.testeducation.ui.delegates.tests.answersDisplayDelegateDefault
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
            simpleDiffUtil(QuestionItemUi::id),
            answersDisplayDelegateDefault()
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
        }
    }

    private fun observeData() = viewModel.observe(this, ::render)

    private fun render(state: TestEditorState) = binding {
        when (state) {
            is TestEditorState.Content -> {
                questionAdapter.items = state.testDetails.questions
                tvTitleTest.text = state.testDetails.title
                tvTypeTest.text = state.testDetails.theme.title
            }
            is TestEditorState.NoInit -> {

            }
        }
    }

}