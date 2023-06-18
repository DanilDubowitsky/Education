package com.testeducation.ui.delegates.tests

import android.graphics.Color
import com.testeducation.logic.model.test.CardTestStyle
import com.testeducation.ui.R
import com.testeducation.ui.databinding.ViewHolderTestShortBinding
import com.testeducation.logic.model.test.TestShortUI
import com.testeducation.ui.utils.hideView
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.showView
import com.testeducation.ui.utils.simpleDelegateAdapter

fun createTestShortAdapterDelegate() = simpleDelegateAdapter<TestShortUI,
        TestShortUI,
        ViewHolderTestShortBinding>(
    ViewHolderTestShortBinding::inflate
) {
    bind {
        binding.cardTest.setContent(item)
    }
}