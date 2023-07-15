package com.testeducation.ui.delegates.tests

import com.testeducation.ui.databinding.ViewHolderTestShortBinding
import com.testeducation.ui.utils.simpleDelegateAdapter

fun createTestShortAdapterDelegate(
    onLikeClick: (position: Int) -> Unit
) = simpleDelegateAdapter(
    ViewHolderTestShortBinding::inflate
) {
    bind {
        binding.cardTest.setContent(item)
        binding.cardTest.setOnLikeClickListener {
            onLikeClick(adapterPosition)
        }
    }
}