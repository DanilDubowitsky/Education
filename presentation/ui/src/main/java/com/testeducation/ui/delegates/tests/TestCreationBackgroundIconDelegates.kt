package com.testeducation.ui.delegates.tests

import android.content.res.ColorStateList
import com.testeducation.logic.model.test.CardTestStyle
import com.testeducation.logic.model.test.IconDesignItem
import com.testeducation.ui.databinding.ViewHolderIconDesignItemBinding
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.loadDrawable
import com.testeducation.ui.utils.simpleDelegateAdapter

fun testCreationBackgroundIconDelegates(onClickSelected: (CardTestStyle) -> Unit) =
    simpleDelegateAdapter<IconDesignItem,
            IconDesignItem,
            ViewHolderIconDesignItemBinding>(
        ViewHolderIconDesignItemBinding::inflate
    ) {
        bind {
            binding.invoke {
                containerImageIcon.backgroundTintList = ColorStateList.valueOf(item.backgroundColor)
                imgIcon.setImageDrawable(context.loadDrawable(item.image))
                root.setOnClickListener {
                    onClickSelected(item.style)
                }
            }
        }
    }