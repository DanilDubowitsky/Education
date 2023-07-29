package com.testeducation.ui.delegates.tests

import androidx.annotation.ColorRes
import com.testeducation.logic.model.theme.ThemeShortUI
import com.testeducation.ui.R
import com.testeducation.ui.databinding.ViewHolderThemeBinding
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.setClickListener
import com.testeducation.ui.utils.simpleDelegateAdapter

fun createThemeShortAdapterDelegate(
    @ColorRes backgroundColorRes: Int = R.color.selector_color_chip,
    onClick: (String) -> Unit,
) = simpleDelegateAdapter<ThemeShortUI,
        ThemeShortUI,
        ViewHolderThemeBinding>(
    ViewHolderThemeBinding::inflate
) {
    binding.root.setClickListener {
        onClick(item.id)
    }
    bind {
        binding {
            root.setChipBackgroundColorResource(backgroundColorRes)
            root.text = item.title
            root.isChecked = item.isSelected
        }
    }
}
