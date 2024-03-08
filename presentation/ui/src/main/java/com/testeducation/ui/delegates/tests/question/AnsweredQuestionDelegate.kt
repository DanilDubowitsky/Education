package com.testeducation.ui.delegates.tests.question

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.google.android.flexbox.FlexboxLayout
import com.testeducation.logic.model.question.AnswerStateUI
import com.testeducation.logic.model.question.AnsweredQuestionUI
import com.testeducation.logic.model.test.AnswerUI
import com.testeducation.ui.R
import com.testeducation.ui.databinding.ViewAnswerAnsweredQuestionBinding
import com.testeducation.ui.databinding.ViewHolderAnsweredMatchQuestionBinding
import com.testeducation.ui.databinding.ViewHolderSimpleResultBinding
import com.testeducation.ui.databinding.ViewTestResultAnswerBinding
import com.testeducation.ui.databinding.ViewTestResultsAnswerItemBinding
import com.testeducation.ui.utils.dp
import com.testeducation.ui.utils.loadColor
import com.testeducation.ui.utils.setClickListener
import com.testeducation.ui.utils.simpleDelegateAdapter

fun choiceAnsweredQuestionDelegate() =
    simpleDelegateAdapter<AnsweredQuestionUI.Choose, AnsweredQuestionUI,
            ViewHolderSimpleResultBinding>(
        ViewHolderSimpleResultBinding::inflate
    ) {
        bind {
            bindSimpleData(
                item.title,
                binding.txtTitle,
                item.numberQuestion,
                binding.imgAnswerIndicator,
                null,
                item.chosenAnswer,
                item.correctAnswer,
                item.state,
                binding.answerChipGroup,
                binding.trueAnswerChip,
            )
        }
    }

fun orderAnsweredQuestionDelegate() =
    simpleDelegateAdapter<AnsweredQuestionUI.Order, AnsweredQuestionUI,
            ViewHolderSimpleResultBinding>(
        ViewHolderSimpleResultBinding::inflate
    ) {
        bind {
            bindSimpleData(
                item.title,
                binding.txtTitle,
                item.numberQuestion,
                binding.imgAnswerIndicator,
                null,
                item.answeredAnswers,
                item.correctOrderAnswers,
                item.state,
                binding.answerChipGroup,
                binding.trueAnswerChip,
            )
        }
    }

fun textAnswerDelegate() = simpleDelegateAdapter<AnsweredQuestionUI.Text, AnsweredQuestionUI,
        ViewHolderSimpleResultBinding>(ViewHolderSimpleResultBinding::inflate) {
    bind {
        bindSimpleData(
            item.title,
            binding.txtTitle,
            item.numberQuestion,
            binding.imgAnswerIndicator,
            item.answered,
            emptyList(),
            listOf(item.correctAnswer),
            item.state,
            binding.answerChipGroup,
            binding.trueAnswerChip,
        )
    }
}

fun matchAnsweredQuestionDelegate(
    onClick: (String) -> Unit,
    onTrueExpandClick: (String) -> Unit
) = simpleDelegateAdapter<AnsweredQuestionUI.Match, AnsweredQuestionUI,
        ViewHolderAnsweredMatchQuestionBinding>(
    ViewHolderAnsweredMatchQuestionBinding::inflate
) {
    binding.root.setClickListener {
        onClick(item.id)
    }
    binding.btnExpand.setClickListener {
        onClick(item.id)
    }
    binding.btnExpandTrueAnswer.setClickListener {
        onTrueExpandClick(item.id)
    }
    bind {
        with(binding) {
            if (item.isTrueExpanded) {
                btnExpandTrueAnswer.scaleY = -1f
            } else {
                btnExpandTrueAnswer.scaleY = 1f
            }

            if (item.isExpanded) {
                btnExpand.scaleY = -1f
            } else {
                btnExpand.scaleY = 1f
            }

            matchDataLayout.isGone = !item.isExpanded
            trueMatchDataLayout.isGone = !item.isTrueExpanded || item.state == AnswerStateUI.CORRECT
            val inflater = LayoutInflater.from(root.context)
            matchDataLayout.removeAllViews()
            trueMatchDataLayout.removeAllViews()
            item.matchValues.forEachIndexed { index, value ->
                val view = ViewAnswerAnsweredQuestionBinding.inflate(inflater)
                view.matchDataText.root.text = value
                view.answerDataText.root.text = item.matchAnswers[index].title
                matchDataLayout.addView(view.root)
            }

            if (item.state == AnswerStateUI.INCORRECT) {
                txtTrueAnswer.isGone = false
                btnExpandTrueAnswer.isGone = false
                item.correctAnswers.forEach { item ->
                    val view = ViewAnswerAnsweredQuestionBinding.inflate(inflater)
                    view.matchDataText.root.text = item.matchedCorrectText
                    view.answerDataText.root.text = item.title
                    trueMatchDataLayout.addView(view.root)
                }
            } else {
                txtTrueAnswer.isGone = true
                btnExpandTrueAnswer.isGone = true
            }

            bindSimpleData(
                item.title,
                txtTitle,
                item.numberQuestion,
                imgAnswerIndicator,
                null,
                emptyList(),
                emptyList(),
                item.state,
                null,
                null,
                txtAnswerIndicator
            )
        }
    }
}

private fun bindSimpleData(
    title: String,
    txtTitle: TextView,
    number: Int,
    imgAnswerIndicator: ImageView,
    customAnswer: String?,
    answers: List<AnswerUI?>,
    correctAnswers: List<AnswerUI?>,
    stateUI: AnswerStateUI,
    answersLayout: FlexboxLayout?,
    correctAnswersLayout: FlexboxLayout?,
    answerTextView: TextView? = null
) {
    val inflater = LayoutInflater.from(txtTitle.context)
    val answer = answers.firstOrNull()
    var txtAnswerIndicator = answerTextView

    answersLayout?.removeAllViews()
    correctAnswersLayout?.removeAllViews()

    if (txtAnswerIndicator == null) {
        txtAnswerIndicator = ViewTestResultAnswerBinding.inflate(inflater).root
    }
    val txtCorrectAnswer = ViewTestResultAnswerBinding.inflate(inflater).root
    txtCorrectAnswer.text =
        txtCorrectAnswer.context.getString(R.string.test_pass_results_correct_answer)
    txtCorrectAnswer.setTextColor(txtCorrectAnswer.context.loadColor(R.color.colorDarkGreen))

    answersLayout?.addView(txtAnswerIndicator)
    correctAnswersLayout?.addView(txtCorrectAnswer)

    if (customAnswer != null) {
        val item = ViewTestResultsAnswerItemBinding.inflate(inflater)
        item.root.text = customAnswer
        item.root.createLayoutParams()
        answersLayout?.addView(item.root)
    }
    answers.forEachIndexed { index, answerUI ->
        val item = ViewTestResultsAnswerItemBinding.inflate(inflater)
        val answerTitle = when (answerUI) {
            is AnswerUI.ChoiceAnswer -> answerUI.title
            is AnswerUI.MatchAnswer -> answerUI.title
            is AnswerUI.OrderAnswer -> "${index + 1}. ${answerUI.title}"
            is AnswerUI.TextAnswer -> customAnswer

            else -> return@forEachIndexed
        }
        item.root.text = answerTitle
        item.root.createLayoutParams()
        answersLayout?.addView(item.root)
    }
    txtTitle.text = txtTitle.context.getString(
        R.string.test_pass_results_title_template,
        number.toString(),
        title
    )
    when (stateUI) {
        AnswerStateUI.CORRECT -> {
            txtAnswerIndicator.setTextColor(txtTitle.context.loadColor(R.color.colorDarkGreenLight))
            imgAnswerIndicator.setImageResource(R.drawable.ic_correct_answer)
            imgAnswerIndicator.colorFilter = null
        }

        AnswerStateUI.INCORRECT -> {
            txtAnswerIndicator.setTextColor(txtTitle.context.loadColor(R.color.colorRed))
            imgAnswerIndicator.setImageResource(R.drawable.ic_incorrect_answer)
            imgAnswerIndicator.colorFilter = null
        }

        AnswerStateUI.NONE -> {
            txtAnswerIndicator.setTextColor(txtTitle.context.loadColor(R.color.colorGrayBlue))
            imgAnswerIndicator.setImageResource(R.drawable.ic_correct_answer)
            imgAnswerIndicator.setColorFilter(txtTitle.context.loadColor(R.color.colorGrayBlue))
        }

        AnswerStateUI.TIME_EXPIRED -> {
            txtAnswerIndicator.setTextColor(txtTitle.context.loadColor(R.color.colorRed))
            imgAnswerIndicator.setImageResource(R.drawable.ic_incorrect_answer)
            imgAnswerIndicator.colorFilter = null
        }
    }
    val isCorrectVisible = stateUI == AnswerStateUI.INCORRECT
    txtCorrectAnswer.isVisible = isCorrectVisible
    correctAnswersLayout?.isVisible = isCorrectVisible
    correctAnswersLayout?.let {
        correctAnswers.forEachIndexed { index, answerUI ->
            val item = ViewTestResultsAnswerItemBinding.inflate(inflater)
            val answerTitle = when (answerUI) {
                is AnswerUI.ChoiceAnswer -> answerUI.title
                is AnswerUI.MatchAnswer -> answerUI.title
                is AnswerUI.OrderAnswer -> "${index + 1}. ${answerUI.title}"
                is AnswerUI.TextAnswer -> answerUI.correctText

                else -> return@forEachIndexed
            }
            item.root.text = answerTitle
            item.root.createLayoutParams()
            correctAnswersLayout.addView(item.root)
        }
    }
}

private fun View.createLayoutParams() {
    layoutParams = FlexboxLayout.LayoutParams(
        FlexboxLayout.LayoutParams.WRAP_CONTENT,
        FlexboxLayout.LayoutParams.WRAP_CONTENT
    ).apply {
        setMargins(8.dp, 0.dp, 8.dp, 5.dp)
    }
}
