package com.testeducation.ui.delegates.tests.question

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.forEach
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.chip.Chip
import com.testeducation.logic.model.question.AnswerStateUI
import com.testeducation.logic.model.question.AnsweredQuestionUI
import com.testeducation.logic.model.test.AnswerUI
import com.testeducation.ui.R
import com.testeducation.ui.databinding.ViewAnswerAnsweredQuestionBinding
import com.testeducation.ui.databinding.ViewHolderAnsweredMatchQuestionBinding
import com.testeducation.ui.databinding.ViewHolderSimpleResultBinding
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
                binding.txtAnswerIndicator,
                binding.imgAnswerIndicator,
                null,
                listOf(item.chosenAnswer),
                listOf(item.correctAnswer),
                item.state,
                binding.answerChipGroup,
                binding.trueAnswerChip,
                binding.txtTrueAnswer
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
                binding.txtAnswerIndicator,
                binding.imgAnswerIndicator,
                null,
                item.answeredAnswers,
                item.correctOrderAnswers,
                item.state,
                binding.answerChipGroup,
                binding.trueAnswerChip,
                binding.txtTrueAnswer
            )
        }
    }

fun matchAnsweredQuestionDelegate(
    onClick: (String) -> Unit
) = simpleDelegateAdapter<AnsweredQuestionUI.Match, AnsweredQuestionUI,
        ViewHolderAnsweredMatchQuestionBinding>(
    ViewHolderAnsweredMatchQuestionBinding::inflate
) {
    binding.root.setClickListener {
        onClick(item.id)
        binding.btnExpand.animate().rotationBy(180f)
    }
    binding.btnExpand.setClickListener {
        onClick(item.id)
        binding.btnExpand.animate().rotationBy(180f)
    }
    bind {
        with(binding) {
            if (!item.isExpanded) {
                btnExpand.rotationY = 0f
            } else {
                btnExpand.rotationY = 180f
            }
            matchDataLayout.isGone = !item.isExpanded
            val inflater = LayoutInflater.from(root.context)
            matchDataLayout.removeAllViews()
            item.matchValues.forEachIndexed { index, value ->
                val view = ViewAnswerAnsweredQuestionBinding.inflate(inflater)
                view.matchDataText.root.text = value
                view.answerDataText.root.text = item.matchAnswers[index].title
                matchDataLayout.addView(view.root)
            }
            bindSimpleData(
                item.title,
                txtTitle,
                item.numberQuestion,
                txtAnswerIndicator,
                imgAnswerIndicator,
                null,
                emptyList(),
                emptyList(),
                item.state,
                null,
                null,
                null
            )
        }
    }
}

private fun bindSimpleData(
    title: String,
    txtTitle: TextView,
    number: Int,
    txtAnswerIndicator: TextView,
    imgAnswerIndicator: ImageView,
    customAnswer: String?,
    answers: List<AnswerUI>,
    correctAnswers: List<AnswerUI>,
    stateUI: AnswerStateUI,
    answersLayout: FlexboxLayout?,
    correctAnswersLayout: FlexboxLayout?,
    txtCorrectAnswer: TextView?
) {
    val inflater = LayoutInflater.from(txtTitle.context)
    val answer = answers.firstOrNull()
    answersLayout?.forEach { view ->
        if (view is Chip) answersLayout.removeView(view)
    }
    correctAnswersLayout?.forEach { view ->
        if (view is Chip) correctAnswersLayout.removeView(view)
    }

    answers.forEachIndexed { index, answerUI ->
        val item = ViewTestResultsAnswerItemBinding.inflate(inflater)
        val answerTitle = when (answerUI) {
            is AnswerUI.ChoiceAnswer -> answerUI.title
            is AnswerUI.MatchAnswer -> answerUI.title
            is AnswerUI.OrderAnswer -> "${index + 1}. ${answerUI.title}"
            is AnswerUI.TextAnswer -> customAnswer
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
        }

        AnswerStateUI.INCORRECT -> {
            txtAnswerIndicator.setTextColor(txtTitle.context.loadColor(R.color.colorRed))
            imgAnswerIndicator.setImageResource(R.drawable.ic_incorrect_answer)
        }

        AnswerStateUI.NONE -> {
            txtAnswerIndicator.setTextColor(txtTitle.context.loadColor(R.color.colorGrayBlue))
            imgAnswerIndicator.setImageResource(R.drawable.ic_correct_answer)
        }
    }
    val isCorrectVisible = answer is AnswerUI.ChoiceAnswer && stateUI == AnswerStateUI.INCORRECT
    txtCorrectAnswer?.isVisible = isCorrectVisible
    correctAnswersLayout?.isVisible = isCorrectVisible
    correctAnswersLayout?.let {
        if (answer !is AnswerUI.ChoiceAnswer) return
        val item = ViewTestResultsAnswerItemBinding.inflate(inflater)
        item.root.text = (correctAnswers.first() as AnswerUI.ChoiceAnswer).title
        item.root.createLayoutParams()
        correctAnswersLayout.addView(item.root)
    }
}

private fun View.createLayoutParams() {
    layoutParams = FlexboxLayout.LayoutParams(
        FlexboxLayout.LayoutParams.WRAP_CONTENT,
        FlexboxLayout.LayoutParams.WRAP_CONTENT
    ).apply {
        setMargins(8.dp, 5.dp, 8.dp, 0)
    }
}
