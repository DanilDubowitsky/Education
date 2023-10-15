package com.testeducation.domain.model.test

sealed class TestSettingsElement {
    abstract val id: Int

    data class TestInput(
        override val id: Int,
        val title: String,
        val valueInput: String
    ) : TestSettingsElement()

    data class Design(
        override val id: Int,
        val title: String,
        val color: String,
        val image: String
    ) : TestSettingsElement()

    data class HorizontalScroll(
        override val id: Int,
        val title: String,
        val list: List<Item>
    ) : TestSettingsElement() {
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
    ) : TestSettingsElement() {
        data class Item(
            val title: String,
            val value: String,
            val isSelected: Boolean
        )

        object Position {
            const val FIRST = 1
            const val SECOND = 2
        }
    }

    data class Selectable(
        override val id: Int,
        val title: String,
        val description: String,
        val isSelected: Boolean
    ) : TestSettingsElement()
}
