package com.testeducation.ui.delegates.tests.answer

import android.content.res.ColorStateList
import com.testeducation.logic.model.test.AnswerUI
import com.testeducation.ui.databinding.ViewHolderAnswerChoiceBinding
import com.testeducation.ui.utils.setClickListener
import com.testeducation.ui.utils.simpleDelegateAdapter

fun createChoiceAnswerDelegate(
    onSelectAnswer: (Int) -> Unit
) = simpleDelegateAdapter<AnswerUI.ChoiceAnswer, AnswerUI, ViewHolderAnswerChoiceBinding>(
        ViewHolderAnswerChoiceBinding::inflate
    ) {
        binding.checkBoxAnswer.setClickListener(needDisable = false) {
            onSelectAnswer(absoluteAdapterPosition)
        }
        bind {
            binding.bindChoiceAnswer(item)
        }
    }

private fun ViewHolderAnswerChoiceBinding.bindChoiceAnswer(
    item: AnswerUI.ChoiceAnswer
) {
    root.backgroundTintList = ColorStateList.valueOf(item.color)
    txtAnswerText.text = item.title
    checkBoxAnswer.isChecked = item.isSelected
}
