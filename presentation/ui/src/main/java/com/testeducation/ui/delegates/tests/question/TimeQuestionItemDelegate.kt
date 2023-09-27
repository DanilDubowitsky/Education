package com.testeducation.ui.delegates.tests.question

import androidx.core.view.isVisible
import com.testeducation.logic.model.question.TimeQuestionUi
import com.testeducation.ui.databinding.ViewHolderTimeQuestionBinding
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.simpleDelegateAdapter

fun timeQuestionItemDelegate(selectionOnClick: (TimeQuestionUi) -> Unit) =
    simpleDelegateAdapter<TimeQuestionUi,
            TimeQuestionUi,
            ViewHolderTimeQuestionBinding>(
        ViewHolderTimeQuestionBinding::inflate
    ) {
        bind {
            binding {
                tvTime.text = item.time
                imgCheck.isVisible = item.isSelected
            }
        }
    }