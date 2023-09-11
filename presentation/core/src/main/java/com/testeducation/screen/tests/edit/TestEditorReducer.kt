package com.testeducation.screen.tests.edit

import com.testeducation.converter.test.getTestStyle
import com.testeducation.converter.test.question.toUi
import com.testeducation.converter.test.toUI
import com.testeducation.core.IReducer
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.logic.model.test.TestDetailsUi
import com.testeducation.logic.model.test.TestStyleUi
import com.testeducation.logic.screen.tests.edit.TestEditorState

class TestEditorReducer(resourceHelper: IResourceHelper) :
    IReducer<TestEditorModelState, TestEditorState> {
    override fun reduce(modelState: TestEditorModelState): TestEditorState {
        val test = modelState.testDetails
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
                questions = test.questions.toUi(),
                status = test.status,
                likes = test.likes,
                liked = test.liked,
                passesUser = test.passesUser,
                passed = test.passed,
                theme = test.theme.toUI()
            )
        )
    }
}