package com.testeducation.ui.screen.tests.creation

import androidx.recyclerview.widget.DiffUtil
import com.testeducation.logic.model.test.AnswerCreationUI

class QuestionCreationDiffUtil : DiffUtil.ItemCallback<AnswerCreationUI>() {

    override fun areItemsTheSame(oldItem: AnswerCreationUI, newItem: AnswerCreationUI): Boolean {
        return when (oldItem) {
            is AnswerCreationUI.DefaultAnswer -> newItem is AnswerCreationUI.DefaultAnswer && oldItem.id == newItem.id
            is AnswerCreationUI.TextAnswer -> newItem is AnswerCreationUI.TextAnswer && oldItem.id == newItem.id
            is AnswerCreationUI.MatchAnswer -> newItem is AnswerCreationUI.MatchAnswer && oldItem.id == newItem.id
            is AnswerCreationUI.OrderAnswer -> newItem is AnswerCreationUI.OrderAnswer && oldItem.id == newItem.id
            is AnswerCreationUI.FooterPlusAdd -> newItem is AnswerCreationUI.FooterPlusAdd && oldItem.id == newItem.id
        }
    }

    override fun areContentsTheSame(oldItem: AnswerCreationUI, newItem: AnswerCreationUI): Boolean {
        return when (oldItem) {
            is AnswerCreationUI.DefaultAnswer -> newItem is AnswerCreationUI.DefaultAnswer && oldItem == newItem
            is AnswerCreationUI.TextAnswer -> newItem is AnswerCreationUI.TextAnswer && oldItem == newItem
            is AnswerCreationUI.MatchAnswer -> newItem is AnswerCreationUI.MatchAnswer && oldItem == newItem
            is AnswerCreationUI.OrderAnswer -> newItem is AnswerCreationUI.OrderAnswer && oldItem == newItem
            is AnswerCreationUI.FooterPlusAdd -> newItem is AnswerCreationUI.FooterPlusAdd && oldItem == newItem
        }
    }
}