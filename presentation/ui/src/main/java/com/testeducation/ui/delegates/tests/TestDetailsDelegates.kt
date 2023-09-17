package com.testeducation.ui.delegates.tests

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.testeducation.logic.model.question.QuestionItemUi
import com.testeducation.logic.model.test.AnswerItemUi
import com.testeducation.logic.model.test.QuestionTypeUi
import com.testeducation.ui.databinding.ViewHolderAnswerDefaultDisplayBinding
import com.testeducation.ui.databinding.ViewHolderQuestionBinding
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.simpleDelegateAdapter

fun answersDisplayDelegateDefault(
) = simpleDelegateAdapter<QuestionItemUi,
        QuestionItemUi,
        ViewHolderQuestionBinding>(
    ViewHolderQuestionBinding::inflate
) {
    bind {
        binding {
            tvQuestion.text = item.title
            icon.setImageResource(item.icon)
            tvNumberTitle.text = item.numberQuestion
            listAnswer.apply {
                removeAllViews()
                item.initAnswerDefault(context, this)
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