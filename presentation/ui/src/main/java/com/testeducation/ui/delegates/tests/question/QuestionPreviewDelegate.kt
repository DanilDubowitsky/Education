package com.testeducation.ui.delegates.tests.question

import com.testeducation.logic.model.question.QuestionItemUi
import com.testeducation.ui.databinding.ViewHolderQuestionPreviewBinding
import com.testeducation.ui.utils.simpleDelegateAdapter

fun createQuestionPreviewDelegate() =
    simpleDelegateAdapter<QuestionItemUi, QuestionItemUi, ViewHolderQuestionPreviewBinding>(
        ViewHolderQuestionPreviewBinding::inflate
    ) {
        bind {
            binding.bind(item)
        }
    }

private fun ViewHolderQuestionPreviewBinding.bind(item: QuestionItemUi) {
    txtQuestion.text = item.title
    txtQuestionNumber.text = item.numberQuestion
    txtTime.text = item.time
}