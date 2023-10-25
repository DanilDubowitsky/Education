package com.testeducation.ui.screen.tests.pass

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.core.view.isGone
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.testeducation.logic.model.question.QuestionUI
import com.testeducation.logic.model.test.AnswerUI
import com.testeducation.logic.screen.tests.pass.TestPassingSideEffect
import com.testeducation.logic.screen.tests.pass.TestPassingState
import com.testeducation.screen.tests.pass.TestPassingViewModel
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentTestPassBinding
import com.testeducation.ui.delegates.tests.answer.createChoiceAnswerDelegate
import com.testeducation.ui.helper.TimeHandler
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.observe
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
    }

    private fun observeData() = viewModel.observe(this, ::render, ::onSideEffect)

    private fun onSideEffect(sideEffect: TestPassingSideEffect) = when (sideEffect) {
        is TestPassingSideEffect.StartQuestionTimer -> binding.startQuestion(sideEffect.time)
        is TestPassingSideEffect.StartTestTimer -> binding.startTest(sideEffect.time)
    }

    private fun setupViews() = binding {
        answersRecycler.adapter = answersAdapter

    }

    private fun render(state: TestPassingState) = binding {
        txtQuestion.text = state.currentQuestion?.title
        if (state.currentQuestion != null) {
            renderAnswers(state.currentQuestion!!)
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
