package com.testeducation.logic.screen.tests.edit

import com.testeducation.logic.model.test.TestDetailsUi

sealed class TestEditorState {
    data class Content(
        val testDetails: TestDetailsUi
    ) : TestEditorState()

    object NoInit : TestEditorState()
}