package com.testeducation.ui.delegates.tests.question

import android.content.res.ColorStateList
import com.testeducation.logic.model.test.AnswerIndicatorItemUi
import com.testeducation.ui.databinding.ViewHolderAnswerOrderIndicatorBinding
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.simpleDelegateAdapter

fun answerIndicatorItemDelegate() =
    simpleDelegateAdapter<AnswerIndicatorItemUi,
            AnswerIndicatorItemUi,
            ViewHolderAnswerOrderIndicatorBinding>(
        ViewHolderAnswerOrderIndicatorBinding::inflate
    ) {
        binding {
            bind {
                tvNumber.text = item.text
                root.backgroundTintList = ColorStateList.valueOf(item.color)
            }
        }
    }
