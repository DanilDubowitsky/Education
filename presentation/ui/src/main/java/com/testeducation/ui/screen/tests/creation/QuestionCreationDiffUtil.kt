package com.testeducation.ui.screen.tests.creation

import androidx.recyclerview.widget.DiffUtil
import com.testeducation.logic.model.test.AnswerCreationUI

class QuestionCreationDiffUtil : DiffUtil.ItemCallback<AnswerCreationUI>() {

    override fun areItemsTheSame(oldItem: AnswerCreationUI, newItem: AnswerCreationUI): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AnswerCreationUI, newItem: AnswerCreationUI): Boolean {
        return oldItem == newItem
    }
}