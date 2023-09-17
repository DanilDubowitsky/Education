package com.testeducation.logic.screen.tests.edit

import com.testeducation.logic.model.question.QuestionDetailsUi
import com.testeducation.logic.model.test.TestDetailsUi

sealed class TestEditorState {
    data class Content(
        val testDetails: TestDetailsUi,
        val questionDetailsUi: List<QuestionDetailsUi>
    ) : TestEditorState()

    object NoInit : TestEditorState()
}