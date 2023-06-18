package com.testeducation.ui.delegates.tests

import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import com.testeducation.logic.model.test.IconDesignItem
import com.testeducation.ui.databinding.ViewHolderIconDesignItemBinding
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.simpleDelegateAdapter

fun testCreationBackgroundIconDelegates() = simpleDelegateAdapter<IconDesignItem,
        IconDesignItem,
        ViewHolderIconDesignItemBinding>(
    ViewHolderIconDesignItemBinding::inflate
) {
    bind {
        binding.invoke {
            containerImageIcon.setBackgroundColor(
                item.backgroundColor
            )
            containerImageIcon.backgroundTintList = ColorStateList.valueOf(item.backgroundColor)
        }
    }
}