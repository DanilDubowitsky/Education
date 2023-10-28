package com.testeducation.screen.tests.edit.style

import com.testeducation.converter.test.toUISelected
import com.testeducation.core.IReducer
import com.testeducation.domain.model.theme.ThemeShort
import com.testeducation.logic.model.test.CardTestStyle
import com.testeducation.logic.model.test.TestShortUI
import com.testeducation.logic.screen.tests.settings.TestStyleChangerState

class TestStyleChangerReducer : IReducer<TestStyleChangerModelState, TestStyleChangerState> {
    override fun reduce(modelState: TestStyleChangerModelState): TestStyleChangerState {
        return TestStyleChangerState(
            //TODO сделать конвертер
            iconDesignList = modelState.iconDesign,
            colorTestCard = modelState.colorState.color,
            testShortUI = createTestShortUI(
                modelState.title,
                modelState.selectedTheme,
                modelState.colorState.color,
                modelState.styleCurrent,
                // TODO: use another model for test creation
                // TODO: add with creation
                TestShortUI.Test.Settings(TestShortUI.Test.Settings.Availability.PUBLIC, true)
            ),
        )
    }

    // TODO: use another model for test creation
    private fun createTestShortUI(
        title: String = "",
        themeShort: ThemeShort,
        color: String,
        style: CardTestStyle,
        settings: TestShortUI.Test.Settings
    ) = TestShortUI.Test(
        id = "",
        title = title,
        questionsCount = 0,
        isPublic = false,
        likes = 0,
        passesCount = 0,
        theme = themeShort.toUISelected(),
        color = color,
        style = style,
        liked = false,
        passed = false
    )

}