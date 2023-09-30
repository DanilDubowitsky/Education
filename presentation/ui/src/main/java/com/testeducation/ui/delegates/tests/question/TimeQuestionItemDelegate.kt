package com.testeducation.ui.delegates.tests.question

import androidx.core.view.isInvisible
import com.testeducation.logic.model.question.TimeQuestionUi
import com.testeducation.ui.databinding.ViewHolderTimeQuestionBinding
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.simpleDelegateAdapter

fun timeQuestionItemDelegate(selectionOnClick: (Int) -> Unit) =
    simpleDelegateAdapter<TimeQuestionUi,
            TimeQuestionUi,
            ViewHolderTimeQuestionBinding>(
        ViewHolderTimeQuestionBinding::inflate
    ) {
        bind {
            binding {
                tvTime.text = item.time
                tvTime.setTextColor(item.color)
                imgCheck.isInvisible = !item.isSelected
                root.setOnClickListener {
                    selectionOnClick(adapterPosition)
                }
            }
        }
    }