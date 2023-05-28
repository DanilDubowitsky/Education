package com.testeducation.ui.delegates.tests

import com.testeducation.ui.R
import com.testeducation.ui.databinding.ViewHolderTestShortBinding
import com.testeducation.logic.model.test.TestShortUI
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.simpleDelegateAdapter

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
