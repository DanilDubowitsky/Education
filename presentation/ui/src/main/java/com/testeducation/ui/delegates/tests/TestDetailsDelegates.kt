package com.testeducation.ui.delegates.tests

import android.view.LayoutInflater
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
            if (item.questionTypeUiItem.type == QuestionTypeUi.DEFAULT) {
                for (itemAnswer in item.answerItemUiList) {
                    if (itemAnswer is AnswerItemUi.DefaultAnswer) {
                        val inflate = ViewHolderAnswerDefaultDisplayBinding.inflate(LayoutInflater.from(context), listAnswer, true)
                        inflate.tvText.text = itemAnswer.answerText
                    }
                }
            }
        }
    }
}