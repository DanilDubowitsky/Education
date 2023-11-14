package com.testeducation.ui.delegates.tests.question

import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup
import com.testeducation.logic.model.question.AnswerStateUI
import com.testeducation.logic.model.question.AnsweredQuestionUI
import com.testeducation.logic.model.test.AnswerUI
import com.testeducation.ui.R
import com.testeducation.ui.databinding.ViewHolderSimpleResultBinding
import com.testeducation.ui.utils.loadColor
import com.testeducation.ui.utils.simpleDelegateAdapter
import kotlin.random.Random

fun simpleAnsweredQuestionDelegate() =
    simpleDelegateAdapter<AnsweredQuestionUI.Choose, AnsweredQuestionUI,
            ViewHolderSimpleResultBinding>(
        ViewHolderSimpleResultBinding::inflate
    ) {

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
    chipGroup: ChipGroup,
    correctChipGroup: ChipGroup?,
    txtCorrectAnswer: TextView?
) {
    val answer = answers.first()
    chipGroup.removeAllViews()

    answers.forEachIndexed { index, answerUI ->
        val chipDrawableS =
            ChipDrawable.createFromAttributes(chipGroup.context, null, 0, R.style.ChipStyleGray)
        val chip = Chip(
            chipGroup.context,
        ).apply {
            id = index
            setChipDrawable(chipDrawableS)
            val answerTitle = when (answerUI) {
                is AnswerUI.ChoiceAnswer -> "$index. ${answerUI.title}"
                is AnswerUI.MatchAnswer -> answerUI.title
                is AnswerUI.OrderAnswer -> answerUI.title
                is AnswerUI.TextAnswer -> customAnswer
            }
            text = answerTitle
            setTextColor(context.getColorStateList(R.color.selector_color_chip_text_color))
        }
        chipGroup.addView(chip)
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
    txtCorrectAnswer?.isVisible =
        answer is AnswerUI.ChoiceAnswer && stateUI == AnswerStateUI.INCORRECT
    correctChipGroup?.let {
        if (answer !is AnswerUI.ChoiceAnswer) return
        val chipDrawableS =
            ChipDrawable.createFromAttributes(chipGroup.context, null, 0, R.style.ChipStyleGray)
        val chip = Chip(
            chipGroup.context,
        ).apply {
            id = Random.nextInt()
            setChipDrawable(chipDrawableS)
            text = (correctAnswers.first() as AnswerUI.ChoiceAnswer).title
            setTextColor(context.getColorStateList(R.color.selector_color_chip_text_color))
        }
        chipGroup.addView(chip)
    }
}
