package com.testeducation.screen.tests.edit.publish

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.tests.edit.TestPublishSideEffect
import com.testeducation.logic.screen.tests.edit.TestPublishState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import org.orbitmvi.orbit.syntax.simple.intent

class TestPublishViewModel(
    exceptionHandler: IExceptionHandler,
    reducer: IReducer<TestPublishModelState, TestPublishState>,
    val isPublish: Boolean,
    private val router: NavigationRouter
) : BaseViewModel<TestPublishModelState, TestPublishState, TestPublishSideEffect>(
    reducer,
    exceptionHandler
) {
    override val initialModelState: TestPublishModelState = TestPublishModelState()

    init {
        init()
    }

    fun save() = intent {
        val modelState = getModelState()
        val status = if (modelState.status == TestPublishModelState.StatusPublish.PUBLISH) {
            NavigationScreen.Tests.TestPublish.OnTestPublish.Result.StatusPublish.PUBLISH
        } else NavigationScreen.Tests.TestPublish.OnTestPublish.Result.StatusPublish.DRAFT
        router.sendResult(NavigationScreen.Tests.TestPublish.OnTestPublish, NavigationScreen.Tests.TestPublish.OnTestPublish.Result(
            statusPublish = status
        ))
        router.exit()
    }

    fun changeStatus(status: TestPublishState.StatusPublish) = intent {
        val statusSelected = when (status) {
            TestPublishState.StatusPublish.PUBLISH -> TestPublishModelState.StatusPublish.PUBLISH
            else -> TestPublishModelState.StatusPublish.DRAFT
        }
        updateModelState {
            copy(
                status = statusSelected
            )
        }
    }

    private fun init() = intent {
        val status = if (isPublish) {
            TestPublishModelState.StatusPublish.PUBLISH
        } else {
            TestPublishModelState.StatusPublish.DRAFT
        }
        updateModelState {
            copy(
                status = status
            )
        }
    }
}