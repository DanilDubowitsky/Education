package com.testeducation.ui.screen.tests.pass

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.ItemTouchHelper
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.testeducation.logic.model.question.AnswerStateUI
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
import com.testeducation.ui.screen.common.LoaderDialog
import com.testeducation.ui.utils.animateTranslationXAndAlpha
import com.testeducation.ui.utils.disableChangeAnimation
import com.testeducation.ui.utils.hideKeyboard
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.isFadeGone
import com.testeducation.ui.utils.loadColor
import com.testeducation.ui.utils.loadDrawable
import com.testeducation.ui.utils.observe
import com.testeducation.ui.utils.setClickListener
import com.testeducation.ui.utils.trimmedTextOrEmpty
import java.text.SimpleDateFormat
import java.util.Collections
import java.util.Locale

class TestPassingFragment : ViewModelHostFragment<TestPassingViewModel, FragmentTestPassBinding>(
    TestPassingViewModel::class,
    FragmentTestPassBinding::inflate
) {

    private val questionTimer = TimeHandler()
    private val testTimer = TimeHandler()
    private var currentQuestionId: String? = null
    private var backgroundTint: ColorStateList? = null

    //TODO: butusov.k move to router
    private var dialogLoader: LoaderDialog? = null

    private val answersAdapter by lazy {
        ListDelegationAdapter(
            createChoiceAnswerDelegate(
                viewModel::selectChoiceAnswer,
                viewModel::onAnswerClick
            ),
            createOrderAnswerDelegate(orderDragListener, viewModel::onAnswerClick),
            createMatchAnswerDelegate(orderDragListener, viewModel::onAnswerClick)
        )
    }

    private val orderDragListener: IDragStartListener = DragStartListener()

    private val answersMatchAdapter by lazy {
        ListDelegationAdapter(createMatchDataDelegate(viewModel::onMatchClick))
    }

    private val questionItemTouchHelperCallback by lazy {
        QuestionItemTouchHelperCallback(
            updateResultMove = { oldPosition, newPosition ->
                val mutableItems = answersAdapter.items!!.toMutableList()
                Collections.swap(mutableItems, oldPosition, newPosition)
                answersAdapter.notifyItemMoved(oldPosition, newPosition)
                answersAdapter.items = mutableItems
            },
            onClearView = { holder ->
                holder.itemView.backgroundTintList = backgroundTint
            },
            onSelectChanged = { holder ->
                backgroundTint = holder.itemView.backgroundTintList
                holder.itemView.backgroundTintList =
                    ColorStateList.valueOf(
                        requireContext().loadColor(R.color.colorGrayBlueDisabled)
                    )
            },
            onDragStateChanged = { _, _, _ ->
                val ids = answersAdapter.items!!.map(AnswerUI::id)
                viewModel.swapAnswers(ids)
            }
        )
    }

    override fun onResume() {
        super.onResume()
        viewModel.increaseResumeCount(testTimer.getRemainingTime())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.statusBarColor = requireContext().loadColor(
            android.R.color.transparent
        )
        setupViews()
        observeData()
        setupListeners()
    }

    private fun observeData() = viewModel.observe(this, ::render, ::onSideEffect)

    private fun onSideEffect(sideEffect: TestPassingSideEffect) = when (sideEffect) {
        is TestPassingSideEffect.StartQuestionTimer -> binding.startQuestion(sideEffect.time)
        is TestPassingSideEffect.StartTestTimer -> binding.startTest(sideEffect.time)
        TestPassingSideEffect.EndTimer -> {
            questionTimer.stop()
            testTimer.stop()
        }
        is TestPassingSideEffect.Loading -> handleLoaderEffect(sideEffect)
    }

    private fun handleLoaderEffect(effect: TestPassingSideEffect.Loading) = when (effect) {
        is TestPassingSideEffect.Loading.ShowLoader -> {
            dialogLoader?.dismiss()
            dialogLoader = LoaderDialog.Builder(requireContext())
                .setCancelable(false)
                .setTitleText(getString(R.string.test_pass_sending_result))
                .show()
        }

        is TestPassingSideEffect.Loading.HideLoader -> {
            dialogLoader?.dismiss()
            dialogLoader = null
        }

    }


    private fun setupViews() = binding {
        answersRecycler.disableChangeAnimation()
        answersOrderingRecycler.disableChangeAnimation()
        answersRecycler.adapter = answersAdapter
        answersOrderingRecycler.adapter = answersMatchAdapter
        val itemTouchHelper = ItemTouchHelper(questionItemTouchHelperCallback)
        orderDragListener.itemTouchHelper = itemTouchHelper
        itemTouchHelper.attachToRecyclerView(answersRecycler)
    }

    private fun setupListeners() = binding {
        btnAnswer.setClickListener {
            val time = questionTimer.getRemainingTime()
            questionTimer.release()
            viewModel.submitAnswer(time, testTimer.getRemainingTime(), false)
        }
        answerText.addTextChangedListener {
            viewModel.onAnswerTextChanged(answerText.trimmedTextOrEmpty)
        }
        btnClose.setClickListener(viewModel::exit)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun render(state: TestPassingState) = binding {
        rootScroll.isInvisible = state.isLoading
        loadingProgress.setVisibility(state.isLoading)
        answerText.isEnabled = state.currentQuestion?.isAnswered()?.not() ?: true
        btnAnswer.isGone = state.isLoading
        answerStatusLayout.isFadeGone =
            state.currentQuestion?.answerState == AnswerStateUI.NONE || state.isLoading
        if (state.currentQuestion != null) {
            renderAnswers(state.currentQuestion!!, state.matchData)
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
            val progress =
                (state.currentQuestionPosition.toFloat() / state.questionsCount.toFloat()) * 100
            questionsProgress.setProgress(progress.toInt(), true)
        }
        backgroundView.isGone = state.isLoading
    }

    private fun FragmentTestPassBinding.bindQuestionAnswerStatus(questionUI: QuestionUI) {
        txtCorrectAnswer.isGone = true
        val statusImage: Drawable?
        val textColor: Int
        val statusText: String?

        when (questionUI.answerState) {
            AnswerStateUI.CORRECT -> {
                statusImage = requireContext().loadDrawable(R.drawable.ic_correct_answer)
                textColor = requireContext().loadColor(R.color.colorDarkGreen)
                statusText = getString(R.string.test_pass_correct_label)
            }
            AnswerStateUI.INCORRECT -> {
                statusImage = requireContext().loadDrawable(R.drawable.ic_incorrect_answer)
                textColor = requireContext().loadColor(R.color.colorRed)
                statusText = getString(R.string.test_pass_incorrect_label)
            }
            AnswerStateUI.NONE -> {
                statusImage = null
                textColor = requireContext().loadColor(R.color.colorRed)
                statusText = null
            }
            AnswerStateUI.TIME_EXPIRED -> {
                statusImage = requireContext().loadDrawable(R.drawable.ic_incorrect_answer)
                textColor = requireContext().loadColor(R.color.colorRed)
                statusText = getString(R.string.test_pass_time_expired)
            }
        }
        txtAnswerStatus.text = statusText
        txtAnswerStatus.setTextColor(textColor)
        imgCorrectIndicator.setImageDrawable(statusImage)
        answerText.hideKeyboard()
        if (questionUI is QuestionUI.Choice) {
            txtCorrectAnswer.isVisible = questionUI.answerState == AnswerStateUI.INCORRECT
            val correctText =
                questionUI.answers.filter(AnswerUI.ChoiceAnswer::isTrue)
                    .joinToString(transform = AnswerUI.ChoiceAnswer::title)
            val displayCorrectText = getString(R.string.test_pass_true_answer_label, correctText)
            txtCorrectAnswer.text = displayCorrectText
        }
    }

    private fun renderAnswers(
        question: QuestionUI,
        matchData: List<TestPassingState.MatchDataUI>
    ) = binding {
        fun setAnswersData() {
            txtQuestion.text = question.title
            when (question) {
                is QuestionUI.Choice -> {
                    answersAdapter.items = question.answers
                }

                is QuestionUI.Match -> {
                    answersAdapter.items = question.answers
                }

                is QuestionUI.Order -> {
                    answersAdapter.items = question.answers
                }

                is QuestionUI.Text -> {}
            }
            answersAdapter.notifyDataSetChanged()
            if (answersMatchAdapter.items != matchData) {
                answersMatchAdapter.items = matchData
                answersMatchAdapter.notifyDataSetChanged()
            }
            answersOrderingRecycler.isGone =
                question !is QuestionUI.Order && question !is QuestionUI.Match
            answersRecycler.isGone = question is QuestionUI.Text
            answerTextLayout.isVisible = question is QuestionUI.Text
        }

        if (currentQuestionId != null && currentQuestionId != question.id) {
            rootScroll.animateTranslationXAndAlpha(
                ANIMATION_DURATION,
                -TRANSLATION_X,
                0f
            ) {
                setAnswersData()
                answerText.text = null
                rootScroll.animateTranslationXAndAlpha(ANIMATION_DURATION, 0f, 1f)
            }
        } else {
            setAnswersData()
        }
        currentQuestionId = question.id
    }

    private fun FragmentTestPassBinding.startTest(time: Long) {
        if (time == 0L) {
            txtTotalTime.text = INFINITY_SYMBOL.toString()
            return
        }
        val dateFormatter = SimpleDateFormat(MINUTES_SECONDS_FORMAT, Locale.getDefault())
        testTimer.setOnUpdateListener { remainingTime ->
            txtTotalTime.text = dateFormatter.format(remainingTime)
        }
        testTimer.setOnExpireListener {
            viewModel.completeTest(testTimer.getRemainingTime())
        }
        testTimer.start(time, TIME_INTERVAL)
    }

    private fun FragmentTestPassBinding.startQuestion(time: Long) {
        if (time == 0L) {
            txtQuestionTime.text = INFINITY_SYMBOL.toString()
            timeQuestionProgress.setProgress(100, true)
            return
        }
        val dateFormatter = SimpleDateFormat(MINUTES_SECONDS_FORMAT, Locale.getDefault())
        questionTimer.setOnUpdateListener { remainingTime ->
            val progress = (remainingTime.toFloat() / time.toFloat()) * 100
            timeQuestionProgress.setProgress(progress.toInt(), true)
            txtQuestionTime.text = dateFormatter.format(remainingTime)
        }
        questionTimer.setOnExpireListener {
            val remainingTime = questionTimer.getRemainingTime()
            viewModel.submitAnswer(remainingTime, testTimer.getRemainingTime(), true)
        }
        questionTimer.start(time, TIME_INTERVAL)
    }

    private fun QuestionUI.isAnswered() = answerState != AnswerStateUI.NONE

    override fun onDestroy() {
        questionTimer.release()
        testTimer.release()
        super.onDestroy()
    }

    private companion object {
        const val TIME_INTERVAL = 1000L
        const val TRANSLATION_X = 100f
        const val ANIMATION_DURATION = 200L
        const val INFINITY_SYMBOL = '\u221e'
        const val MINUTES_SECONDS_FORMAT = "mm:ss"
    }
}
