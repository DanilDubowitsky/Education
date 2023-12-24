package com.testeducation.screen.tests.code.share

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.test.GetTestCode
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.tests.code.share.TestCodeShareSideEffect
import com.testeducation.logic.screen.tests.code.share.TestCodeShareState
import com.testeducation.navigation.core.NavigationRouter
import org.orbitmvi.orbit.syntax.simple.intent

class TestCodeShareViewModel(
    reducer: IReducer<TestCodeShareModelState, TestCodeShareState>,
    exceptionHandler: IExceptionHandler,
    private val router: NavigationRouter,
    private val testId: String,
    private val getTestCode: GetTestCode
) : BaseViewModel<TestCodeShareModelState, TestCodeShareState, TestCodeShareSideEffect>(
    reducer,
    exceptionHandler
) {

    override val initialModelState: TestCodeShareModelState = TestCodeShareModelState()

    init {
        loadData()
    }

    fun exit() {
        router.exit()
    }

    private fun loadData() = intent {
        val code = getTestCode(testId)
        updateModelState {
            copy(
                code = code,
                isLoading = false
            )
        }
    }

}
