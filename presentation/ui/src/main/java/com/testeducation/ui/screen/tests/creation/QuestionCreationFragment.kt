package com.testeducation.ui.screen.tests.creation

import android.os.Bundle
import android.view.View
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.testeducation.logic.model.test.QuestionTypeUi
import com.testeducation.logic.screen.tests.creation.question.creation.QuestionCreationState
import com.testeducation.screen.tests.creation.question.creation.QuestionCreationViewModel
import com.testeducation.ui.R
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentQuestionCreationBinding
import com.testeducation.ui.delegates.tests.question.answerDelegateWrite
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
            answersDelegateDefault(
                onClickCheckTrue = viewModel::changeCheckedAnswer,
                onClickDelete = viewModel::deleteAnswer
            ),
            answerDelegateWrite(),
            footerPlusAddDelegate(viewModel::addAnswer)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding {
            rvAnswers {
                adapter = questionAdapter
                disableChangeAnimation()
            }
            containerQuestionType.setOnClickListener {
                viewModel.changeTypeQuestion()
            }
        }
        viewModel.observe(this, ::render)
    }

    private fun render(questionCreationState: QuestionCreationState) = binding {
        questionAdapter.items = questionCreationState.answerItemUiList
        when (questionCreationState.questionTypeUiItem.type) {
            QuestionTypeUi.MATCH -> {
                imgIconQuestionType.setImageResource(R.drawable.ic_answer_match)
                tvTitleQuestionType.text = getString(R.string.question_type_match)
            }

            QuestionTypeUi.DEFAULT -> {
                imgIconQuestionType.setImageResource(R.drawable.ic_answer_choosing)
                tvTitleQuestionType.text = getString(R.string.question_type_answer)
            }

            QuestionTypeUi.WRITE_ANSWER -> {
                imgIconQuestionType.setImageResource(R.drawable.ic_answer_write)
                tvTitleQuestionType.text = getString(R.string.question_type_write_answer)
            }
        }
    }
}