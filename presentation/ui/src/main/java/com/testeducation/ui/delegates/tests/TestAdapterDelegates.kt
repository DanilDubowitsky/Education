package com.testeducation.ui.delegates.tests

import com.testeducation.logic.model.test.TestShortUI
import com.testeducation.ui.databinding.ViewHolderSimpleLoaderBinding
import com.testeducation.ui.databinding.ViewHolderTestShortBinding
import com.testeducation.ui.databinding.ViewHolderTestShortPagerBinding
import com.testeducation.ui.utils.setClickListener
import com.testeducation.ui.utils.simpleDelegateAdapter

fun createTestShortAdapterDelegate(
    onLikeClick: (position: Int) -> Unit,
    onClick: (String) -> Unit
) = simpleDelegateAdapter<TestShortUI.Test, TestShortUI, ViewHolderTestShortBinding>(
    ViewHolderTestShortBinding::inflate
) {
    binding.root.setClickListener {
        onClick(item.id)
    }
    binding.cardTest.setOnLikeClickListener {
        onLikeClick(absoluteAdapterPosition)
    }
    bind {
        binding.cardTest.setContent(item)
    }
}

fun createTestLoadingDelegate() =
    simpleDelegateAdapter<TestShortUI.Loader, TestShortUI, ViewHolderSimpleLoaderBinding>(
        ViewHolderSimpleLoaderBinding::inflate
    ) {}

fun createTestShortPagerDelegate(
    onClick: (String) -> Unit,
    onLikeClick: (position: Int) -> Unit
) = simpleDelegateAdapter<TestShortUI.Test, TestShortUI,
        ViewHolderTestShortPagerBinding>(
    ViewHolderTestShortPagerBinding::inflate
) {

    binding.root.setClickListener {
        onClick(item.id)
    }

    binding.cardTest.setOnLikeClickListener {
        onLikeClick(absoluteAdapterPosition)
    }

    bind {
        binding.cardTest.setContent(item)
    }

}
