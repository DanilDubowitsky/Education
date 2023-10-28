package com.testeducation.screen.tests.edit

import com.testeducation.converter.test.getTestStyle
import com.testeducation.converter.test.question.toUIModels
import com.testeducation.converter.test.question.toUi
import com.testeducation.converter.test.toUI
import com.testeducation.core.IReducer
import com.testeducation.helper.question.ITimeConverterLongToString
import com.testeducation.logic.model.test.TestDetailsUi
import com.testeducation.logic.model.test.TestStyleUi
import com.testeducation.logic.screen.tests.edit.TestEditorState

class TestEditorReducer(private val timeConverterLongToString: ITimeConverterLongToString) :
    IReducer<TestEditorModelState, TestEditorState> {
    override fun reduce(modelState: TestEditorModelState): TestEditorState {
        val test = modelState.test
        return if (test == null) {
            TestEditorState.NoInit
        } else TestEditorState.Content(
            testDetails = TestDetailsUi(
                id = test.id,
                title = test.title,
                style = TestStyleUi(
                    color = test.style.color,
                    background = getTestStyle(test.style.background)
                ),
                settings = test.settings.toUI(),
                questions = emptyList(),
                status = test.status.name,
                likes = test.likes,
                liked = test.liked,
                passesUser = test.passesUser,
                passed = test.passed,
                theme = test.theme.toUI()
            ),
            questionDetailsUi = modelState.questionDetails.toUi(timeConverterLongToString)
        )
    }
}