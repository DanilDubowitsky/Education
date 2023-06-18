package com.testeducation.ui.delegates.tests

import android.graphics.drawable.Drawable
import com.testeducation.logic.model.theme.ThemeShortUI
import com.testeducation.ui.R
import com.testeducation.ui.databinding.ViewHolderThemeBinding
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.loadDrawable
import com.testeducation.ui.utils.setClickListener
import com.testeducation.ui.utils.simpleDelegateAdapter

fun createThemeShortAdapterDelegate(
    uncheckedBackground: Drawable? = null,
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
            root.text = item.title
            val backgroundDrawable =
                if (item.isSelected) context.loadDrawable(R.drawable.background_selected_theme)
                else {
                    uncheckedBackground ?:
                    context.loadDrawable(R.drawable.background_selectable_theme_white)
                }
            root.background = backgroundDrawable
        }
    }
}
