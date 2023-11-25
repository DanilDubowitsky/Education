package com.testeducation.ui.view.custom

import androidx.core.view.isVisible
import com.testeducation.logic.model.test.AvatarItemUI
import com.testeducation.ui.databinding.ViewHolderAvatarBinding
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.simpleDelegateAdapter

fun avatarChangerViewAdapterDelegate(onClickSelected: (Int) -> Unit) =
    simpleDelegateAdapter<AvatarItemUI,
            AvatarItemUI,
            ViewHolderAvatarBinding>(
        ViewHolderAvatarBinding::inflate
    ) {
        bind {
            binding {
                imgIcon.setImageResource(item.avatarDrawableId)
                imgBorder.isVisible = item.isSelected
                root.setOnClickListener {
                    onClickSelected.invoke(absoluteAdapterPosition)
                }
            }
        }
    }