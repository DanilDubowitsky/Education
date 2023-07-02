package com.testeducation.screen.tests.questions

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.tests.questions.QuestionEditorSideEffect
import com.testeducation.logic.screen.tests.questions.QuestionEditorState

class QuestionEditorViewModel(
    reducer: IReducer<QuestionEditorModelState, QuestionEditorState>,
    errorHandler: IExceptionHandler
) : BaseViewModel<QuestionEditorModelState, QuestionEditorState, QuestionEditorSideEffect>(
    reducer,
    errorHandler
) {
    override val initialModelState: QuestionEditorModelState = QuestionEditorModelState()



}