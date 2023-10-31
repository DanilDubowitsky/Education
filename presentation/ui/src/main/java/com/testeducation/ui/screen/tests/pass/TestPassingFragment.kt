package com.testeducation.ui.screen.tests.pass

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ItemTouchHelper
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.testeducation.logic.model.question.QuestionUI
import com.testeducation.logic.model.test.AnswerUI
import com.testeducation.logic.screen.tests.pass.TestPassingSideEffect
import com.testeducation.logic.screen.tests.pass.TestPassingState
import com.testeducation.screen.tests.pass.TestPassingViewModel
import com.testeducation.ui.R
import com.testeducation.ui.base.fragment.ViewModelHostFragment
import com.testeducation.ui.databinding.FragmentTestPassBinding
import com.testeducation.ui.delegates.tests.answer.createChoiceAnswerDelegate
import com.testeducation.ui.delegates.tests.answer.createMatchAnswerDelegate
import com.testeducation.ui.delegates.tests.answer.createMatchDataDelegate
import com.testeducation.ui.delegates.tests.answer.createOrderAnswerDelegate
import com.testeducation.ui.helper.TimeHandler
import com.testeducation.ui.listener.QuestionItemTouchHelperCallback
import com.testeducation.ui.listener.drag.DragStartListener
import com.testeducation.ui.listener.drag.IDragStartListener
import com.testeducation.ui.utils.disableChangeAnimation
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

    private val questionTimer = TimeHandler()
    private val testTimer = TimeHandler()

    private val answersAdapter by lazy {
        AsyncListDifferDelegationAdapter(
            simpleDiffUtil(AnswerUI::id),
            createChoiceAnswerDelegate(viewModel::selectChoiceAnswer),
            createOrderAnswerDelegate(dragStartListener),
            createMatchAnswerDelegate()
        )
    }

    private val dragStartListener: IDragStartListener = DragStartListener()

    private val answersMatchAdapter by lazy {
        ListDelegationAdapter(createMatchDataDelegate())
    }

    private val questionItemTouchHelperCallback by lazy {
        QuestionItemTouchHelperCallback(
            updateResultMove = viewModel::swapAnswers,
            onClearView = {}
        )
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
        answersRecycler.disableChangeAnimation()
        answersOrderingRecycler.disableChangeAnimation()
        answersRecycler.adapter = answersAdapter
        answersOrderingRecycler.adapter = answersMatchAdapter
        val itemTouchHelper = ItemTouchHelper(questionItemTouchHelperCallback)
        dragStartListener.itemTouchHelper = itemTouchHelper
        itemTouchHelper.attachToRecyclerView(answersRecycler)
    }

    private fun setupListeners() = binding {
        btnAnswer.setClickListener {
            val time = questionTimer.getRemainingTime()
            questionTimer.release()
            viewModel.submitAnswer(time)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun render(state: TestPassingState) = binding {
        txtQuestion.text = state.currentQuestion?.title
        answersOrderingRecycler.isGone = state.matchData.isEmpty()

        println("CURRENT_QUESTION: ${state.currentQuestion}")

        if (answersMatchAdapter.items != state.matchData) {
            answersMatchAdapter.items = state.matchData
            answersMatchAdapter.notifyDataSetChanged()
        }

        if (state.currentQuestion != null) {
            renderAnswers(state.currentQuestion!!)
            bindQuestionAnswerStatus(state.currentQuestion!!)
            btnAnswer.text = if (state.currentQuestion!!.isAnswered()) {
                getString(R.string.test_pass_next_label)
            } else {
                getString(R.string.test_pass_answer_label)
            }
            txtQuestionProgress.text = getString(
                R.string.test_pass_question_count,
                state.currentQuestionPosition.toString(),
                state.questionsCount.toString()
            )
        }
        answerStatusLayout.isGone =
            state.currentQuestion?.answerState == QuestionUI.AnswerState.NONE
    }

    private fun FragmentTestPassBinding.bindQuestionAnswerStatus(questionUI: QuestionUI) {
        txtCorrectAnswer.isGone = true
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
            txtCorrectAnswer.isVisible = questionUI.answerState == QuestionUI.AnswerState.INCORRECT
            val correctText = questionUI.answers.first { choiceAnswer ->
                choiceAnswer.isTrue
            }.title
            val displayCorrectText = getString(R.string.test_pass_true_answer_label, correctText)
            txtCorrectAnswer.text = displayCorrectText
        }
    }

    private fun renderAnswers(question: QuestionUI) = binding {
        answersOrderingRecycler.isGone =
            question !is QuestionUI.Order && question !is QuestionUI.Match

        when (question) {
            is QuestionUI.Choice -> answersAdapter.items = question.answers
            is QuestionUI.Match -> answersAdapter.items = question.answers
            is QuestionUI.Order -> answersAdapter.items = question.answers
            is QuestionUI.Text -> TODO()
        }
    }

    private fun FragmentTestPassBinding.startTest(time: Long) {
        val dateFormatter = SimpleDateFormat("mm:ss", Locale.getDefault())
        testTimer.setOnUpdateListener { remainingTime ->
            binding.txtTotalTime.text = dateFormatter.format(remainingTime)
        }
        testTimer.start(time, TIME_INTERVAL)
    }

    private fun FragmentTestPassBinding.startQuestion(time: Long) {
        val dateFormatter = SimpleDateFormat("mm:ss", Locale.getDefault())
        questionTimer.setOnUpdateListener { remainingTime ->
            binding.txtQuestionTime.text = dateFormatter.format(remainingTime)
        }
        questionTimer.start(time, TIME_INTERVAL)
    }

    private fun QuestionUI.isAnswered() = answerState != QuestionUI.AnswerState.NONE

    override fun onDestroy() {
        questionTimer.release()
        testTimer.release()
        super.onDestroy()
    }

    private companion object {
        const val TIME_INTERVAL = 1000L
    }

}
