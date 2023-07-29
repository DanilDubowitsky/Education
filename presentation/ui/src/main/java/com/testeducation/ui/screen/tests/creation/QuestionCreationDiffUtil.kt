package com.testeducation.ui.screen.tests.creation

import androidx.recyclerview.widget.DiffUtil
import com.testeducation.logic.model.test.AnswerItemUi

class QuestionCreationDiffUtil : DiffUtil.ItemCallback<AnswerItemUi>() {

    override fun areItemsTheSame(oldItem: AnswerItemUi, newItem: AnswerItemUi): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AnswerItemUi, newItem: AnswerItemUi): Boolean {
        return oldItem == newItem
    }
}