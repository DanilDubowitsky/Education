package com.testeducation.screen.tests.code.enter

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.test.GetTestByCode
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.resource.CodeStringResource
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.logic.screen.tests.code.enter.TestCodeEnterSideEffect
import com.testeducation.logic.screen.tests.code.enter.TestCodeEnterState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect

private typealias Syntax = SimpleSyntax<TestCodeEnterState, TestCodeEnterSideEffect>

class TestCodeEnterViewModel(
    reducer: IReducer<TestCodeEnterModelState, TestCodeEnterState>,
    exceptionHandler: IExceptionHandler,
    private val router: NavigationRouter,
    private val resourceHelper: IResourceHelper,
    private val getTestByCode: GetTestByCode
) : BaseViewModel<TestCodeEnterModelState, TestCodeEnterState, TestCodeEnterSideEffect>(
    reducer, exceptionHandler
) {

    override val initialModelState: TestCodeEnterModelState = TestCodeEnterModelState()

    fun onCodeChange(
        code: String
    ) = intent {
        updateModelState {
            copy(testCode = code)
        }
    }

    fun enter() = intent {
        val modelState = getModelState()
        val isValid = validateCode(modelState.testCode)
        if (!isValid) return@intent
        updateModelState {
            copy(isLoading = true)
        }
        router.exit()
        val screen = NavigationScreen.Tests.Preview(null, modelState.testCode)
        router.navigateTo(screen)
        updateModelState {
            copy(isLoading = false)
        }
    }

    override fun handleThrowable(throwable: Throwable) = intent {
        super.handleThrowable(throwable)
        updateModelState {
            copy(isLoading = false)
        }
    }

    private suspend fun Syntax.validateCode(
        code: String
    ): Boolean {
        if (code.length !in CODE_LENGTH..CODE_LENGTH) {
            val error = TestCodeEnterSideEffect.ShowCodeEnterError(
                resourceHelper.extractStringResource(CodeStringResource.CodeError)
            )
            postSideEffect(error)
            return false
        }
        return true
    }

    private companion object {
        const val CODE_LENGTH = 6
    }

}
