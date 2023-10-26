package com.testeducation.ui.screen.tests.pass

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.testeducation.logic.model.question.QuestionUI
import com.testeducation.logic.model.test.AnswerUI
import com.testeducation.logic.screen.tests.pass.TestPassingSideEffect
import com.testeducation.logic.screen.tests.pass.TestPassingState
import com.testeducation.screen.tests.pass.TestPassingViewModel
import com.testeducation.ui.R
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentTestPassBinding
import com.testeducation.ui.delegates.tests.answer.createChoiceAnswerDelegate
import com.testeducation.ui.helper.TimeHandler
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.loadColor
import com.testeducation.ui.utils.loadDrawable
import com.testeducation.ui.utils.observe
import com.testeducation.ui.utils.setClickListener
import com.testeducation.ui.utils.simpleDiffUtil
import java.text.SimpleDateFormat
import java.util.Locale

class TestPassingFragment : ViewModelHostFragment<TestPassingViewModel, FragmentTestPassBinding>(
    TestPassingViewModel::class,
    FragmentTestPassBinding::inflate
) {

    private val timer = TimeHandler()

    private val timeFormatter by lazy {
        SimpleDateFormat()
    }

    private val answersAdapter by lazy {
        AsyncListDifferDelegationAdapter(
            simpleDiffUtil(AnswerUI::id),
            createChoiceAnswerDelegate(viewModel::selectChoiceAnswer)
        )
    }

    private val answersMatchAdapter by lazy {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeData()
        setupListeners()
    }

    private fun observeData() = viewModel.observe(this, ::render, ::onSideEffect)

    private fun onSideEffect(sideEffect: TestPassingSideEffect) = when (sideEffect) {
        is TestPassingSideEffect.StartQuestionTimer -> binding.startQuestion(sideEffect.time)
        is TestPassingSideEffect.StartTestTimer -> binding.startTest(sideEffect.time)
    }

    private fun setupViews() = binding {
        answersRecycler.adapter = answersAdapter

    }

    private fun setupListeners() = binding {
        btnAnswer.setClickListener {
            val time = timer.getRemainingTime(QUESTION_TIMER_KEY)
            timer.release(QUESTION_TIMER_KEY)
            viewModel.submitAnswer(time)
        }
    }

    private fun render(state: TestPassingState) = binding {
        txtQuestion.text = state.currentQuestion?.title

        if (state.currentQuestion != null) {
            renderAnswers(state.currentQuestion!!)
            bindQuestionAnswerStatus(state.currentQuestion!!)
            btnAnswer.text = if (state.currentQuestion!!.isAnswered()) {
                getString(R.string.test_pass_next_label)
            } else {
                getString(R.string.test_pass_answer_label)
            }
        }
        answerStatusLayout.isGone =
            state.currentQuestion?.answerState == QuestionUI.AnswerState.NONE
    }

    private fun FragmentTestPassBinding.bindQuestionAnswerStatus(questionUI: QuestionUI) {
        txtCorrectAnswer.isGone = false
        val statusImage: Drawable?
        val textColor: Int
        val statusText: String
        if (questionUI.answerState == QuestionUI.AnswerState.CORRECT) {
            statusImage = requireContext().loadDrawable(R.drawable.ic_correct_answer)
            textColor = requireContext().loadColor(R.color.colorDarkGreen)
            statusText = getString(R.string.test_pass_correct_label)
        } else {
            statusImage = requireContext().loadDrawable(R.drawable.ic_incorrect_answer)
            textColor = requireContext().loadColor(R.color.colorRed)
            statusText = getString(R.string.test_pass_incorrect_label)
        }
        txtAnswerStatus.text = statusText
        txtAnswerStatus.setTextColor(textColor)
        imgCorrectIndicator.setImageDrawable(statusImage)
        if (questionUI is QuestionUI.Choice) {
            txtCorrectAnswer.isGone = false
            txtCorrectAnswer.text = questionUI.answers.first { choiceAnswer ->
                choiceAnswer.isTrue
            }.title
        }
    }

    private fun renderAnswers(question: QuestionUI) = binding {
        answersOrderingRecycler.isGone =
            question !is QuestionUI.Order && question !is QuestionUI.Match

        when (question) {
            is QuestionUI.Choice -> answersAdapter.items = question.answers
            is QuestionUI.Match -> TODO()
            is QuestionUI.Order -> TODO()
            is QuestionUI.Text -> TODO()
        }
    }

    private fun FragmentTestPassBinding.startTest(time: Long) {
        val dateFormatter = SimpleDateFormat("mm:ss", Locale.getDefault())
        timer.setOnUpdateListener(TEST_TIMER_KEY) { remainingTime ->
            binding.txtTotalTime.text = dateFormatter.format(remainingTime)
        }
        timer.start(time, TIME_INTERVAL, TEST_TIMER_KEY)
    }

    private fun FragmentTestPassBinding.startQuestion(time: Long) {
        val dateFormatter = SimpleDateFormat("mm:ss", Locale.getDefault())
        timer.setOnUpdateListener(QUESTION_TIMER_KEY) { remainingTime ->
            binding.txtQuestionTime.text = dateFormatter.format(remainingTime)
        }
        timer.start(time, TIME_INTERVAL, QUESTION_TIMER_KEY)
    }

    private fun QuestionUI.isAnswered() = answerState != QuestionUI.AnswerState.NONE

    override fun onDestroy() {
        timer.releaseAll(QUESTION_TIMER_KEY, TEST_TIMER_KEY)
        super.onDestroy()
    }

    private companion object {
        const val QUESTION_TIMER_KEY = "QUESTION_TIMER"
        const val TEST_TIMER_KEY = "TEST_TIMER"
        const val TIME_INTERVAL = 1000L
    }

}
