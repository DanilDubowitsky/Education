package com.example.ui.delegates.tests

import android.util.TypedValue
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
            val typedValue = TypedValue()
            if (item.isSelected){
                context.theme.resolveAttribute(
                    androidx.appcompat.R.attr.colorControlHighlight,
                    typedValue,
                    true
                )
                root.setBackgroundResource(typedValue.resourceId)
            } else root.background = context.loadDrawable(R.drawable.background_selectable_theme)
        }
    }
}
