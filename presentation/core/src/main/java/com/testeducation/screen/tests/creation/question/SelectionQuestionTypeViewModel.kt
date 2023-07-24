package com.testeducation.screen.tests.creation.question

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.theme.GetThemes
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.logic.model.test.QuestionType
import com.testeducation.logic.screen.tests.creation.TestCreationSideEffect
import com.testeducation.logic.screen.tests.creation.TestCreationState
import com.testeducation.logic.screen.tests.creation.question.SelectionQuestionTypeState
import com.testeducation.screen.tests.creation.TestCreationModelState

class SelectionQuestionTypeViewModel(
    reducer: IReducer<SelectionQuestionTypeModelState, SelectionQuestionTypeState>,
    errorHandler: IExceptionHandler,
    private val resourceHelper: IResourceHelper,
    override val initialModelState: SelectionQuestionTypeModelState,
) : BaseViewModel<SelectionQuestionTypeModelState, SelectionQuestionTypeState, TestCreationSideEffect>(
    reducer,
    errorHandler
) {

    init {
        initData()
    }


    fun submit(questionType: QuestionType) {

    }

    private fun initData() {

    }

}