package com.testeducation.screen.tests.code.share

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.tests.code.share.TestCodeShareSideEffect
import com.testeducation.logic.screen.tests.code.share.TestCodeShareState

class TestCodeShareViewModel(
    reducer: IReducer<TestCodeShareModelState, TestCodeShareState>,
    exceptionHandler: IExceptionHandler,
    private val testId: String
) : BaseViewModel<TestCodeShareModelState, TestCodeShareState, TestCodeShareSideEffect>(
    reducer,
    exceptionHandler
) {

    override val initialModelState: TestCodeShareModelState = TestCodeShareModelState()

}
