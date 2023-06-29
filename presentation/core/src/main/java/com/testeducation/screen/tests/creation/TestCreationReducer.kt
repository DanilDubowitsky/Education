package com.testeducation.screen.tests.creation

import com.testeducation.converter.test.createTestShortUI
import com.testeducation.converter.test.toUIModels
import com.testeducation.core.IReducer
import com.testeducation.logic.model.test.CardTestStyle
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
                CardTestStyle.X
            )
        )
    }

}
