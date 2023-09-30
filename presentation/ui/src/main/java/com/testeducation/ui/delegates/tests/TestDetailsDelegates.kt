package com.testeducation.ui.delegates.tests

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.testeducation.logic.model.question.QuestionDetailsUi
import com.testeducation.logic.model.question.QuestionItemUi
import com.testeducation.logic.model.test.AnswerItemUi
import com.testeducation.logic.model.test.QuestionTypeUi
import com.testeducation.ui.databinding.ViewHolderAnswerDefaultDisplayBinding
import com.testeducation.ui.databinding.ViewHolderAnswerFootPlusBinding
import com.testeducation.ui.databinding.ViewHolderAnswerMatchDisplayBinding
import com.testeducation.ui.databinding.ViewHolderAnswerWriteAnswerDisplayBinding
import com.testeducation.ui.databinding.ViewHolderQuestionBinding
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.simpleDelegateAdapter

fun answersDisplayDelegateDefault(
) = simpleDelegateAdapter<QuestionDetailsUi.QuestionItemDetails,
        QuestionDetailsUi,
        ViewHolderQuestionBinding>(
    ViewHolderQuestionBinding::inflate
) {
    bind {
        binding {
            val itemQuestion = item.question
            tvQuestion.text = itemQuestion.title
            icon.setImageResource(itemQuestion.icon)
            tvNumberTitle.text = itemQuestion.numberQuestion
            listAnswer.apply {
                removeAllViews()
                itemQuestion.initAnswerDefault(context, this)
                itemQuestion.initAnswerWriteAnswer(context, this)
                itemQuestion.initAnswerMatchAnswer(context, this)
            }
            tvTime.text = item.question.time
        }
    }
}

fun footerQuestionDetailsPlusAddDelegate(onClickAdd: () -> Unit) =
    simpleDelegateAdapter<QuestionDetailsUi.FooterAdd,
            QuestionDetailsUi,
            ViewHolderAnswerFootPlusBinding>(
        ViewHolderAnswerFootPlusBinding::inflate
    ) {
        bind {
            binding {
                imgPlus.setOnClickListener {
                    onClickAdd()
                }
            }
        }
    }

private fun QuestionItemUi.initAnswerDefault(context: Context, linearLayout: LinearLayout) {
    if (questionTypeUiItem.type == QuestionTypeUi.DEFAULT) {
        for (itemAnswer in answerItemUiList) {
            if (itemAnswer is AnswerItemUi.DefaultAnswer) {
                val inflate = ViewHolderAnswerDefaultDisplayBinding.inflate(LayoutInflater.from(context), linearLayout, true)
                inflate.tvText.text = itemAnswer.answerText
                inflate.imgIndicator.backgroundTintList = ColorStateList.valueOf(
                    itemAnswer.resource.isTrueColor
                )
            }
        }
    }
}

private fun QuestionItemUi.initAnswerWriteAnswer(context: Context, linearLayout: LinearLayout) {
    if (questionTypeUiItem.type == QuestionTypeUi.WRITE_ANSWER) {
        for (itemAnswer in answerItemUiList) {
            if (itemAnswer is AnswerItemUi.TextAnswer) {
                val inflate = ViewHolderAnswerWriteAnswerDisplayBinding.inflate(LayoutInflater.from(context), linearLayout, true)
                inflate.tvText.text = itemAnswer.text
            }
        }
    }
}

private fun QuestionItemUi.initAnswerMatchAnswer(context: Context, linearLayout: LinearLayout) {
    if (questionTypeUiItem.type == QuestionTypeUi.MATCH) {
        for (itemAnswer in answerItemUiList) {
            if (itemAnswer is AnswerItemUi.MatchAnswer) {
                val inflate = ViewHolderAnswerMatchDisplayBinding.inflate(LayoutInflater.from(context), linearLayout, true)
                inflate.tvText.text = itemAnswer.firstAnswer
                inflate.tvTextMatch.text = itemAnswer.secondAnswer
            }
        }
    }
}