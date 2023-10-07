package com.testeducation.ui.delegates.tests.settings

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.testeducation.logic.model.test.CardTestStyle
import com.testeducation.logic.model.test.TestSettingsElementUi
import com.testeducation.logic.model.test.TestShortUI
import com.testeducation.logic.model.theme.ThemeShortUI
import com.testeducation.ui.R
import com.testeducation.ui.databinding.ViewHolderFooterEmptyBinding
import com.testeducation.ui.databinding.ViewHolderSettingsChoiceBinding
import com.testeducation.ui.databinding.ViewHolderSettingsHorizontalScrollBinding
import com.testeducation.ui.databinding.ViewHolderSettingsInputTextBinding
import com.testeducation.ui.databinding.ViewHolderSettingsSelectableBinding
import com.testeducation.ui.databinding.ViewHolderSettingsTestDesignBinding
import com.testeducation.ui.delegates.tests.createThemeShortSettingsAdapterDelegate
import com.testeducation.ui.utils.invoke
import com.testeducation.ui.utils.simpleDelegateAdapter
import com.testeducation.ui.utils.simpleDiffUtil
import com.testeducation.utils.MainColor

fun testSettingsInputTest() =
    simpleDelegateAdapter<TestSettingsElementUi.TestInput,
            TestSettingsElementUi,
            ViewHolderSettingsInputTextBinding>(
        ViewHolderSettingsInputTextBinding::inflate
    ) {
        binding {
            bind {
                tvTitle.text = item.title
                edText.setText(item.valueInput)
            }
        }
    }

fun testSettingsDesign() =
    simpleDelegateAdapter<TestSettingsElementUi.Design,
            TestSettingsElementUi,
            ViewHolderSettingsTestDesignBinding>(
        ViewHolderSettingsTestDesignBinding::inflate
    ) {
        binding {
            bind {
                tvTitle.text = item.title
                cardTest.setContent(TestShortUI.Test(
                    id = "",
                    title = "",
                    questionsCount = 0,
                    isPublic = false,
                    likes = 0,
                    passesCount = 0,
                    theme = ThemeShortUI("", "", false),
                    color = MainColor.colorOrange,
                    style = CardTestStyle.DOTTED,
                    liked = false,
                    passed = false
                ))
            }
        }
    }

private val themesAdapter by lazy {
    AsyncListDifferDelegationAdapter(
        simpleDiffUtil(TestSettingsElementUi.HorizontalScroll.Item::id),
        createThemeShortSettingsAdapterDelegate(
            R.color.selector_color_chip_white,
            onClick = {}
        )
    )
}

fun testSettingsHorizontalScroll() =
    simpleDelegateAdapter<TestSettingsElementUi.HorizontalScroll,
            TestSettingsElementUi,
            ViewHolderSettingsHorizontalScrollBinding>(
        ViewHolderSettingsHorizontalScrollBinding::inflate
    ) {
        binding {
            bind {
                tvTitle.text = item.title
                rvHorizontalScroll.apply {
                    adapter = themesAdapter
                }
                themesAdapter.items = item.list
            }
        }
    }

fun testSettingsChoice() =
    simpleDelegateAdapter<TestSettingsElementUi.Choice,
            TestSettingsElementUi,
            ViewHolderSettingsChoiceBinding>(
        ViewHolderSettingsChoiceBinding::inflate
    ) {
        binding {
            bind {
                tvTitle.text = item.title
                chipFirst.text = item.itemFirst.title
                chipFirst.isChecked = item.itemFirst.isSelected
                chipSecond.text = item.itemSecond.title
                chipSecond.isChecked = item.itemSecond.isSelected
            }
        }
    }

fun testSettingsSelectable() =
    simpleDelegateAdapter<TestSettingsElementUi.Selectable,
            TestSettingsElementUi,
            ViewHolderSettingsSelectableBinding>(
        ViewHolderSettingsSelectableBinding::inflate
    ) {
        binding {
            bind {
                tvTitle.text = item.title
                checkbox.isChecked = item.isSelected
            }
        }
    }

fun footerEmpty() =
    simpleDelegateAdapter<TestSettingsElementUi.FooterEmpty,
            TestSettingsElementUi,
            ViewHolderFooterEmptyBinding>(
        ViewHolderFooterEmptyBinding::inflate
    ) {

    }
