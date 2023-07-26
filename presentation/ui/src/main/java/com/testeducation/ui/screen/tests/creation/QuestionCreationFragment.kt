package com.testeducation.ui.screen.tests.creation

import android.os.Bundle
import android.view.View
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.testeducation.logic.screen.tests.creation.question.creation.QuestionCreationState
import com.testeducation.screen.tests.creation.question.creation.QuestionCreationViewModel
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentQuestionCreationBinding
import com.testeducation.ui.delegates.tests.question.answersDelegateDefault
import com.testeducation.ui.delegates.tests.question.footerPlusAddDelegate
import com.testeducation.ui.utils.disableChangeAnimation
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.observe

class QuestionCreationFragment :
    ViewModelHostFragment<QuestionCreationViewModel, FragmentQuestionCreationBinding>(
        QuestionCreationViewModel::class,
        FragmentQuestionCreationBinding::inflate
    ) {

    private val questionAdapter by lazy {
        AsyncListDifferDelegationAdapter(
            QuestionCreationDiffUtil(),
            answersDelegateDefault(),
            footerPlusAddDelegate()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding {
            rvAnswers {
                adapter = questionAdapter
                disableChangeAnimation()
            }
        }
        viewModel.observe(this, ::render)
    }

    private fun render(questionCreationState: QuestionCreationState) = binding {
        questionAdapter.items = questionCreationState.answerItemUiList
    }
}