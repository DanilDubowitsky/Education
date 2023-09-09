package com.testeducation.screen.tests.edit

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.logic.screen.tests.edit.TestEditorSideEffect
import com.testeducation.logic.screen.tests.edit.TestEditorState

class TestEditorViewModel(
    exceptionHandler: IExceptionHandler,
    reducer: IReducer<TestEditorModelState, TestEditorState>,
    private val resourceHelper: IResourceHelper,
) : BaseViewModel<TestEditorModelState, TestEditorState, TestEditorSideEffect>(
    reducer,
    exceptionHandler
) {
    override val initialModelState: TestEditorModelState = TestEditorModelState()


}