package com.example.ui.delegates.tests

import com.example.logic.model.theme.ThemeShortUI
import com.example.ui.databinding.ViewHolderThemeBinding
import com.example.ui.utils.invoke
import com.example.ui.utils.simpleDelegateAdapter

fun createThemeShortAdapterDelegate() = simpleDelegateAdapter<ThemeShortUI,
        ThemeShortUI,
        ViewHolderThemeBinding>(
    ViewHolderThemeBinding::inflate
) {
    bind {
        binding {
            root.text = item.title
        }
    }
}
