package com.testeducation.ui.delegates.tests.question

import com.testeducation.logic.model.question.QuestionPreviewUI
import com.testeducation.ui.databinding.ViewHolderQuestionPreviewBinding
import com.testeducation.ui.utils.simpleDelegateAdapter

fun createQuestionPreviewDelegate() =
    simpleDelegateAdapter<QuestionPreviewUI, QuestionPreviewUI, ViewHolderQuestionPreviewBinding>(
        ViewHolderQuestionPreviewBinding::inflate
    ) {
        bind {
            binding.bind(item)
        }
    }

private fun ViewHolderQuestionPreviewBinding.bind(item: QuestionPreviewUI) {
    txtQuestion.text = item.title
    txtQuestionNumber.text = item.numberQuestion.toString()
    txtTime.text = item.time
}
