package com.testeducation.ui.screen.tests.list

import androidx.recyclerview.widget.DiffUtil
import com.testeducation.logic.model.test.TestShortUI

class TestShortDiffUtil : DiffUtil.ItemCallback<TestShortUI>() {

    override fun areItemsTheSame(oldItem: TestShortUI, newItem: TestShortUI): Boolean {
        return when (oldItem) {
            is TestShortUI.Test -> newItem is TestShortUI.Test && oldItem.id == newItem.id
            TestShortUI.Loader -> oldItem == newItem
        }
    }

    override fun areContentsTheSame(oldItem: TestShortUI, newItem: TestShortUI): Boolean {
        return when (oldItem) {
            TestShortUI.Loader -> oldItem == newItem
            is TestShortUI.Test -> newItem is TestShortUI.Test && oldItem == newItem
        }
    }
}
