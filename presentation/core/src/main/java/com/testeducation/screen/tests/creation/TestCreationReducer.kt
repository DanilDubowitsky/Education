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
                // TODO: use another model for test creation
                // TODO: add with creation
                TestShortUI.Test.Settings(
                    TestShortUI.Test.Settings.Availability.PUBLIC,
                    true,
                    0,
                    false,
                    0
                )
            ),
            btnCancelText = modelState.backBtnText,
            btnNextText = modelState.nextBtnText,
            visibleProgressBar = modelState.loadingState == TestCreationModelState.LoadingState.LOADING
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
