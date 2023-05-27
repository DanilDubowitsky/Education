package com.example.ui.delegates.tests

import com.example.ui.R
import com.example.ui.databinding.ViewHolderTestShortBinding
import com.example.logic.model.test.TestShortUI
import com.example.ui.utils.invoke
import com.example.ui.utils.simpleDelegateAdapter

fun createTestShortAdapterDelegate() = simpleDelegateAdapter<TestShortUI,
        TestShortUI,
        ViewHolderTestShortBinding>(
    ViewHolderTestShortBinding::inflate
) {
    bind {
        binding {
            themeText.text = item.theme.title
            likesCountText.text = item.likes.toString()
            passesCountText.text = item.passesCount.toString()
            titleText.text = item.title
            questionsCountText.text =
                context.resources.getQuantityString(
                    R.plurals.questions_count_plurals,
                    item.questionsCount,
                    item.questionsCount
                )
        }
    }
}
