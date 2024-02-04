package com.testeducation.ui.delegates.tests.sort

import com.testeducation.logic.model.test.TestSortUI
import com.testeducation.ui.databinding.ViewHolderSortItemBinding
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.setClickListener
import com.testeducation.ui.utils.simpleDelegateAdapter

fun createTestSortDelegate(
    onClick: (TestSortUI) -> Unit
) = simpleDelegateAdapter<TestSortUI, TestSortUI, ViewHolderSortItemBinding>(
    ViewHolderSortItemBinding::inflate
) {
    binding.checkBox.setClickListener {
        onClick(item)
    }
    binding.txtTitle.setClickListener {
        onClick(item)
    }
    bind {
        binding {
            checkBox.isChecked = item.isChecked
            txtTitle.text = item.title
        }
    }
}
