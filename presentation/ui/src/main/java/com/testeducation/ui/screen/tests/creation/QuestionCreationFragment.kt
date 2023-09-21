package com.testeducation.ui.screen.tests.creation

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.testeducation.logic.model.test.QuestionTypeUi
import com.testeducation.logic.screen.tests.creation.question.creation.QuestionCreationSideEffect
import com.testeducation.logic.screen.tests.creation.question.creation.QuestionCreationState
import com.testeducation.screen.tests.creation.question.creation.QuestionCreationViewModel
import com.testeducation.ui.R
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentQuestionCreationBinding
import com.testeducation.ui.delegates.tests.question.answerDelegateMatch
import com.testeducation.ui.delegates.tests.question.answerDelegateWrite
import com.testeducation.ui.delegates.tests.question.answersDelegateDefault
import com.testeducation.ui.delegates.tests.question.footerPlusAddDelegate
import com.testeducation.ui.screen.LoaderDialog
import com.testeducation.ui.utils.disableChangeAnimation
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.observe
import com.testeducation.ui.utils.setClickListener

class QuestionCreationFragment :
    ViewModelHostFragment<QuestionCreationViewModel, FragmentQuestionCreationBinding>(
        QuestionCreationViewModel::class,
        FragmentQuestionCreationBinding::inflate
    ) {

    private var dialogLoader: LoaderDialog? = null

    private val questionAdapter by lazy {
        AsyncListDifferDelegationAdapter(
            QuestionCreationDiffUtil(),
            answersDelegateDefault(
                onClickCheckTrue = viewModel::changeCheckedAnswer,
                onClickDelete = viewModel::deleteAnswer,
                onAnswerTextChanger = viewModel::answerTextChanger
            ),
            answerDelegateWrite(onAnswerTextChanger = viewModel::answerTextChanger),
            answerDelegateMatch(
                onClickDelete = viewModel::deleteAnswer,
                onAnswerTextChanger = viewModel::answerMatchChanger
            ),
            footerPlusAddDelegate(viewModel::addAnswer)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        setupListeners()
        observeData()
        requireActivity().window.statusBarColor = requireContext().getColor(R.color.colorBackground)
        binding {
            etQuestion.doOnTextChanged { text, start, before, count ->
                viewModel.updateQuestionText(text.toString())
            }
            btnCreate.setOnClickListener {
                viewModel.saveQuestion()
            }
        }

    }

    override fun onDestroyView() {
        dialogLoader?.dismiss()
        dialogLoader = null
        super.onDestroyView()
    }

    private fun setupRecycler() = binding {
        rvAnswers {
            adapter = questionAdapter
            disableChangeAnimation()
        }
    }

    private fun observeData() = viewModel.observe(this, ::render, ::onSideEffect)

    private fun setupListeners() = binding {
        containerQuestionType.setClickListener(viewModel::changeTypeQuestion)
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

    private fun onSideEffect(sideEffect: QuestionCreationSideEffect) {
        when (sideEffect) {
            is QuestionCreationSideEffect.LoaderVisible -> {
                dialogLoader?.dismiss()
                dialogLoader = LoaderDialog.Builder(requireContext())
                    .setTitleText(getString(R.string.question_creation_save_data))
                    .setAnimation(R.raw.animation_lmal9dt8).show()
            }

            is QuestionCreationSideEffect.LoaderInvisible -> {
                dialogLoader?.dismiss()
                dialogLoader = null
            }
        }
    }
}