package com.testeducation.ui.delegates.tests.settings

import androidx.core.widget.doOnTextChanged
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
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

fun testSettingsInputTest(update: (Int, String) -> Unit) =
    simpleDelegateAdapter<TestSettingsElementUi.TestInput,
            TestSettingsElementUi,
            ViewHolderSettingsInputTextBinding>(
        ViewHolderSettingsInputTextBinding::inflate
    ) {
        binding {
            edText.doOnTextChanged { text, start, before, count ->
                update(item.id, text.toString())
            }
            bind {
                tvTitle.text = item.title
                edText.setText(item.valueInput)
            }
        }
    }

fun testSettingsDesign(openTestStyleChanger: () -> Unit) =
    simpleDelegateAdapter<TestSettingsElementUi.Design,
            TestSettingsElementUi,
            ViewHolderSettingsTestDesignBinding>(
        ViewHolderSettingsTestDesignBinding::inflate
    ) {
        binding {
            root.setOnClickListener {
                openTestStyleChanger()
            }
            bind {
                tvTitle.text = item.title
                cardTest.setContent(
                    TestShortUI.Test(
                        id = "",
                        title = "",
                        questionsCount = 0,
                        isPublic = false,
                        likes = 0,
                        passesCount = 0,
                        theme = ThemeShortUI("", item.themeName, false),
                        color = item.color,
                        style = item.image,
                        liked = false,
                        passed = false
                    )
                )
            }
        }
    }

fun testSettingsHorizontalScroll(update: (Int, String) -> Unit) =
    simpleDelegateAdapter<TestSettingsElementUi.HorizontalScroll,
            TestSettingsElementUi,
            ViewHolderSettingsHorizontalScrollBinding>(
        ViewHolderSettingsHorizontalScrollBinding::inflate
    ) {
        val themesAdapter by lazy {
            AsyncListDifferDelegationAdapter(
                simpleDiffUtil(TestSettingsElementUi.HorizontalScroll.Item::id),
                createThemeShortSettingsAdapterDelegate(
                    R.color.selector_color_chip_white,
                    onClick = { selectedId -> update(item.id, selectedId) }
                )
            )
        }
        binding {
            bind {
                tvTitle.text = item.title
                rvHorizontalScroll.apply {
                    adapter = themesAdapter
                    itemAnimator = null
                }
                themesAdapter.items = item.list
                rvHorizontalScroll.smoothScrollToPosition(0)
            }
        }
    }

fun testSettingsChoice(update: (Int, Int) -> Unit) =
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
                chipFirst.setOnClickListener {
                    update(
                        item.id, 1
                    )
                }
                chipSecond.setOnClickListener {
                    update(
                        item.id, 2
                    )
                }
            }
        }
    }

fun testSettingsSelectable(update: (Int) -> Unit) =
    simpleDelegateAdapter<TestSettingsElementUi.Selectable,
            TestSettingsElementUi,
            ViewHolderSettingsSelectableBinding>(
        ViewHolderSettingsSelectableBinding::inflate
    ) {
        binding {
            bind {
                tvTitle.text = item.title
                checkbox.isChecked = item.isSelected
                checkbox.setOnClickListener {
                    update(item.id)
                }
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
