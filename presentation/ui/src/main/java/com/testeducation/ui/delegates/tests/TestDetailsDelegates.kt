package com.testeducation.ui.delegates.tests

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.testeducation.logic.model.question.InputQuestionUI
import com.testeducation.logic.model.question.QuestionDetailsUi
import com.testeducation.logic.model.test.AnswerCreationUI
import com.testeducation.logic.model.test.QuestionTypeUi
import com.testeducation.ui.databinding.ViewHolderAnswerDefaultDisplayBinding
import com.testeducation.ui.databinding.ViewHolderAnswerFootPlusBinding
import com.testeducation.ui.databinding.ViewHolderAnswerMatchDisplayBinding
import com.testeducation.ui.databinding.ViewHolderAnswerOrderDisplayBinding
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
                itemQuestion.initAnswerOrderAnswer(context, this)
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

private fun InputQuestionUI.initAnswerDefault(context: Context, linearLayout: LinearLayout) {
    if (questionTypeUiItem.type == QuestionTypeUi.DEFAULT) {
        for (itemAnswer in answerItemUiList) {
            if (itemAnswer is AnswerCreationUI.DefaultAnswer) {
                val inflate = ViewHolderAnswerDefaultDisplayBinding.inflate(
                    LayoutInflater.from(context),
                    linearLayout,
                    true
                )
                inflate.tvText.text = itemAnswer.answerText
                inflate.imgIndicator.backgroundTintList = ColorStateList.valueOf(
                    itemAnswer.resource.isTrueColor
                )
            }
        }
    }
}

private fun InputQuestionUI.initAnswerWriteAnswer(context: Context, linearLayout: LinearLayout) {
    if (questionTypeUiItem.type == QuestionTypeUi.WRITE_ANSWER) {
        for (itemAnswer in answerItemUiList) {
            if (itemAnswer is AnswerCreationUI.TextAnswer) {
                val inflate = ViewHolderAnswerWriteAnswerDisplayBinding.inflate(
                    LayoutInflater.from(context),
                    linearLayout,
                    true
                )
                inflate.tvText.text = itemAnswer.text
            }
        }
    }
}

private fun InputQuestionUI.initAnswerMatchAnswer(context: Context, linearLayout: LinearLayout) {
    if (questionTypeUiItem.type == QuestionTypeUi.MATCH) {
        for (itemAnswer in answerItemUiList) {
            if (itemAnswer is AnswerCreationUI.MatchAnswer) {
                val inflate = ViewHolderAnswerMatchDisplayBinding.inflate(
                    LayoutInflater.from(context),
                    linearLayout,
                    true
                )
                inflate.tvText.text = itemAnswer.firstAnswer
                inflate.tvTextMatch.text = itemAnswer.secondAnswer
            }
        }
    }
}

private fun InputQuestionUI.initAnswerOrderAnswer(context: Context, linearLayout: LinearLayout) {
    if (questionTypeUiItem.type == QuestionTypeUi.ORDER) {
        for (itemAnswer in answerItemUiList) {
            if (itemAnswer is AnswerCreationUI.OrderAnswer) {
                val inflate = ViewHolderAnswerOrderDisplayBinding.inflate(
                    LayoutInflater.from(context),
                    linearLayout,
                    true
                )
                inflate.tvOrder.text = itemAnswer.order.toString()
                inflate.tvText.text = itemAnswer.answerText
                inflate.order.backgroundTintList = ColorStateList.valueOf(itemAnswer.color)
            }
        }
    }
}