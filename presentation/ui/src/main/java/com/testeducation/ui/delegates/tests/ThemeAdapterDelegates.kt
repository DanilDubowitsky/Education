package com.testeducation.ui.delegates.tests

import com.testeducation.logic.screen.theme.ThemeShortUI
import com.testeducation.ui.databinding.ViewHolderThemeBinding
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.simpleDelegateAdapter

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
