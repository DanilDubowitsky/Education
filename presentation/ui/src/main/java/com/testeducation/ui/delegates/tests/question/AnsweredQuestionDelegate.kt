package com.testeducation.ui.delegates.tests.question

import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup
import com.testeducation.logic.model.question.AnswerStateUI
import com.testeducation.logic.model.question.AnsweredQuestionUI
import com.testeducation.logic.model.test.AnswerUI
import com.testeducation.ui.R
import com.testeducation.ui.databinding.ViewAnswerAnsweredQuestionBinding
import com.testeducation.ui.databinding.ViewHolderAnsweredMatchQuestionBinding
import com.testeducation.ui.databinding.ViewHolderSimpleResultBinding
import com.testeducation.ui.utils.loadColor
import com.testeducation.ui.utils.simpleDelegateAdapter
import kotlin.random.Random

fun choiceAnsweredQuestionDelegate() =
    simpleDelegateAdapter<AnsweredQuestionUI.Choose, AnsweredQuestionUI,
            ViewHolderSimpleResultBinding>(
        ViewHolderSimpleResultBinding::inflate
    ) {
        bind {
            bindSimpleData(
                item.title,
                binding.txtTitle,
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

fun matchAnsweredQuestionDelegate() =
    simpleDelegateAdapter<AnsweredQuestionUI.Match, AnsweredQuestionUI,
            ViewHolderAnsweredMatchQuestionBinding>(
        ViewHolderAnsweredMatchQuestionBinding::inflate
    ) {
        bind {
            with(binding) {
                matchDataLayout.removeAllViews()
                item.matchValues.forEachIndexed { index, value ->
                    val view = ViewAnswerAnsweredQuestionBinding.inflate(
                        LayoutInflater.from(root.context)
                    )
                    view.matchDataText.root.text = value
                    view.answerDataText.root.text = item.matchAnswers[index].title
                    matchDataLayout.addView(view.root)
                }
                bindSimpleData(
                    item.title,
                    txtTitle,
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
    txtAnswerIndicator: TextView,
    imgAnswerIndicator: ImageView,
    customAnswer: String?,
    answers: List<AnswerUI>,
    correctAnswers: List<AnswerUI>,
    stateUI: AnswerStateUI,
    chipGroup: ChipGroup?,
    correctChipGroup: ChipGroup?,
    txtCorrectAnswer: TextView?
) {
    val answer = answers.firstOrNull()
    chipGroup?.removeAllViews()
    correctChipGroup?.removeAllViews()

    answers.forEachIndexed { index, answerUI ->
        val chipDrawableS =
            ChipDrawable.createFromAttributes(txtTitle.context, null, 0, R.style.ChipStyleGray)
        val chip = Chip(
            txtTitle.context,
        ).apply {
            id = index
            setChipDrawable(chipDrawableS)
            val answerTitle = when (answerUI) {
                is AnswerUI.ChoiceAnswer -> answerUI.title
                is AnswerUI.MatchAnswer -> answerUI.title
                is AnswerUI.OrderAnswer -> "${index + 1}. ${answerUI.title}"
                is AnswerUI.TextAnswer -> customAnswer
            }
            text = answerTitle
            setTextColor(context.getColorStateList(R.color.selector_color_chip_text_color))
        }
        chipGroup?.addView(chip)
    }
    txtTitle.text = title
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
    correctChipGroup?.isVisible = isCorrectVisible
    correctChipGroup?.let {
        if (answer !is AnswerUI.ChoiceAnswer) return
        val chipDrawableS =
            ChipDrawable.createFromAttributes(
                correctChipGroup.context,
                null,
                0,
                R.style.ChipStyleGray
            )
        val chip = Chip(
            correctChipGroup.context,
        ).apply {
            id = Random.nextInt()
            setChipDrawable(chipDrawableS)
            text = (correctAnswers.first() as AnswerUI.ChoiceAnswer).title
            setTextColor(context.getColorStateList(R.color.selector_color_chip_text_color))
        }
        correctChipGroup.addView(chip)
    }
}
