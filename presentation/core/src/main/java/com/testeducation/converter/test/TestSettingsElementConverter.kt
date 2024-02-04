package com.testeducation.converter.test

import com.testeducation.domain.model.test.TestSettingsElement
import com.testeducation.logic.model.test.CardTestStyle
import com.testeducation.logic.model.test.TestSettingsElementUi

fun List<TestSettingsElement>.toUi() = map {
    when (it) {
        is TestSettingsElement.TestInput -> {
            TestSettingsElementUi.TestInput(
                id = it.id,
                title = it.title,
                valueInput = it.valueInput,
                hint = it.hint
            )
        }

        is TestSettingsElement.Design -> {
            TestSettingsElementUi.Design(
                id = it.id,
                title = it.title,
                color = it.color,
                image = CardTestStyle.valueOf(it.image),
                themeName = it.themeName
            )
        }

        is TestSettingsElement.HorizontalScroll -> {
            TestSettingsElementUi.HorizontalScroll(
                id = it.id,
                title = it.title,
                list = it.list.map { item ->
                    TestSettingsElementUi.HorizontalScroll.Item(
                        id = item.id,
                        title = item.title,
                        isSelected = item.isSelected
                    )
                }
            )
        }

        is TestSettingsElement.Choice -> {
            TestSettingsElementUi.Choice(
                id = it.id,
                title = it.title,
                itemFirst = TestSettingsElementUi.Choice.Item(
                    title = it.itemFirst.title,
                    isSelected = it.itemFirst.isSelected
                ),
                itemSecond = TestSettingsElementUi.Choice.Item(
                    title = it.itemSecond.title,
                    isSelected = it.itemSecond.isSelected
                )
            )
        }

        is TestSettingsElement.Selectable -> {
            TestSettingsElementUi.Selectable(
                id = it.id,
                title = it.title,
                description = it.description,
                isSelected = it.isSelected
            )
        }
    }
}