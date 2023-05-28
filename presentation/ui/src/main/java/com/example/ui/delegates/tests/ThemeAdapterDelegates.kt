package com.example.ui.delegates.tests

import com.example.logic.model.theme.ThemeShortUI
import com.example.ui.R
import com.example.ui.databinding.ViewHolderThemeBinding
import com.example.ui.utils.invoke
import com.example.ui.utils.loadDrawable
import com.example.ui.utils.setClickListener
import com.example.ui.utils.simpleDelegateAdapter

fun createThemeShortAdapterDelegate(
    onClick: (String) -> Unit
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
            root.text = item.title
            val backgroundDrawable =
                if (item.isSelected) context.loadDrawable(R.drawable.background_selected_theme)
                else context.loadDrawable(R.drawable.background_selectable_theme)
            root.background = backgroundDrawable
        }
    }
}
