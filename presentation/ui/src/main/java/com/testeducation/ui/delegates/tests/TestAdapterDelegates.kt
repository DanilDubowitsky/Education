package com.testeducation.ui.delegates.tests

import com.testeducation.logic.model.test.TestShortUI
import com.testeducation.ui.databinding.ViewHolderSimpleLoaderBinding
import com.testeducation.ui.databinding.ViewHolderTestShortBinding
import com.testeducation.ui.utils.simpleDelegateAdapter

fun createTestShortAdapterDelegate(
    onLikeClick: (position: Int) -> Unit
) = simpleDelegateAdapter<TestShortUI.Test, TestShortUI, ViewHolderTestShortBinding>(
    ViewHolderTestShortBinding::inflate
) {
    bind {
        binding.cardTest.setContent(item)
        binding.cardTest.setOnLikeClickListener {
            onLikeClick(adapterPosition)
        }
    }
}

fun createTestLoadingDelegate() =
    simpleDelegateAdapter<TestShortUI.Loader, TestShortUI, ViewHolderSimpleLoaderBinding>(
        ViewHolderSimpleLoaderBinding::inflate
    ) {

    }
