package com.testeducation.logic.model.test

sealed class TestSettingsElementUi {
    abstract val id: Int

    data class TestInput(
        override val id: Int,
        val title: String,
        val valueInput: String
    ) : TestSettingsElementUi()

    data class Design(
        override val id: Int,
        val title: String,
        val color: String,
        val image: String
    ) : TestSettingsElementUi()

    data class HorizontalScroll(
        override val id: Int,
        val title: String,
        val list: List<Item>
    ) : TestSettingsElementUi() {
        data class Item(
            val id: String,
            val title: String,
            val isSelected: Boolean
        )
    }

    data class Choice(
        override val id: Int,
        val title: String,
        val itemFirst: Item,
        val itemSecond: Item
    ) : TestSettingsElementUi() {
        data class Item(
            val title: String,
            val isSelected: Boolean
        )
    }

    data class Selectable(
        override val id: Int,
        val title: String,
        val description: String,
        val isSelected: Boolean
    ) : TestSettingsElementUi()

    data class FooterEmpty(override val id: Int = -1): TestSettingsElementUi()
}