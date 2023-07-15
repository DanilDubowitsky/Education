package com.testeducation.screen.tests.creation

import com.testeducation.converter.test.toUIModels
import com.testeducation.converter.test.toUISelected
import com.testeducation.core.IReducer
import com.testeducation.domain.model.theme.ThemeShort
import com.testeducation.logic.model.test.CardTestStyle
import com.testeducation.logic.model.test.TestShortUI
import com.testeducation.logic.screen.tests.creation.TestCreationState

class TestCreationReducer : IReducer<TestCreationModelState, TestCreationState> {

    override fun reduce(modelState: TestCreationModelState): TestCreationState {
        return TestCreationState(
            isLoading = modelState.loadingState == TestCreationModelState.LoadingState.LOADING,
            themes = modelState.themes.toUIModels(),
            isFirstScreenVisible = modelState.stepState == TestCreationModelState.StepState.FIRST,
            iconDesignList = modelState.iconDesign,
            colorTestCard = modelState.colorState.color,
            testShortUI = createTestShortUI(
                modelState.title,
                modelState.selectedTheme,
                modelState.colorState.color,
                modelState.styleCurrent,
                // TODO: add with creation
                TestShortUI.Settings(TestShortUI.Settings.Availability.PUBLIC, true)
            ),
            btnCancelText = modelState.backBtnText
        )
    }

    private fun createTestShortUI(
        title: String = "",
        themeShort: ThemeShort,
        color: String,
        style: CardTestStyle,
        settings: TestShortUI.Settings
    ) = TestShortUI(
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
        passed = false,
        settings = settings
    )

}
