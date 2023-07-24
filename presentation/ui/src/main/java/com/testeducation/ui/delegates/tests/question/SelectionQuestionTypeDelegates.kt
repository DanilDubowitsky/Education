package com.testeducation.ui.delegates.tests.question

import com.testeducation.logic.model.test.QuestionType
import com.testeducation.logic.model.test.QuestionTypeUiItem
import com.testeducation.ui.databinding.ViewHolderQuestionTypeBinding
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.simpleDelegateAdapter

fun selectionQuestionTypeDelegate(selectionOnClick: (QuestionType) -> Unit) =
    simpleDelegateAdapter<QuestionTypeUiItem,
            QuestionTypeUiItem,
            ViewHolderQuestionTypeBinding>(
        ViewHolderQuestionTypeBinding::inflate
    ) {
        bind {
            binding {
                tvTitle.text = item.title
                imgIcon.setImageResource(item.icon)
            }
        }
    }