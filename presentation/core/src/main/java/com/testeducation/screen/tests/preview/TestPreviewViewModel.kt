package com.testeducation.screen.tests.preview

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.test.GetTest
import com.testeducation.domain.cases.test.GetTests
import com.testeducation.domain.cases.test.ToggleTestLike
import com.testeducation.domain.model.test.TestGetType
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.test.ITestHelper
import com.testeducation.logic.screen.tests.preview.TestPreviewSideEffect
import com.testeducation.logic.screen.tests.preview.TestPreviewState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.screen.home.HomeViewModel
import com.testeducation.screen.home.HomeViewModel.Companion.HOME_NAVIGATOR_KEY
import org.orbitmvi.orbit.syntax.simple.intent

class TestPreviewViewModel(
    reducer: IReducer<TestPreviewModelState, TestPreviewState>,
    exceptionHandler: IExceptionHandler,
    private val testHelper: ITestHelper,
    private val router: NavigationRouter,
    private val testId: String,
    private val getTest: GetTest,
    private val likeTest: ToggleTestLike,
    private val getTests: GetTests
) : BaseViewModel<TestPreviewModelState,
        TestPreviewState, TestPreviewSideEffect>(reducer, exceptionHandler) {

    override val initialModelState: TestPreviewModelState = TestPreviewModelState()

    init {
        loadData()
    }

    fun toggleDescriptionExpand() = intent {
        updateModelState {
            copy(isDescriptionExpanded = !isDescriptionExpanded)
        }
    }

    fun exit() = intent {
        router.exit()
    }

    fun toggleFavorite() = intent {
        val modelState = getModelState()
        val testId = modelState.test?.id ?: return@intent
        val isLiked = modelState.test.liked
        updateModelState {
            copy(test = test?.copy(liked = !isLiked))
        }
        likeTest(testId, !isLiked)
    }

    fun openQuestionsScreen() = intent {
        val modelState = getModelState()

        val screen = NavigationScreen.Questions.QuestionsPreview(
            modelState.test?.id.orEmpty()
        )
        router.navigateTo(screen)
    }

    fun onTestClick(id: String) = intent {
        val screen = NavigationScreen.Tests.Preview(id)
        router.navigateTo(screen, key = HOME_NAVIGATOR_KEY)
    }

    fun onLikeClick(position: Int) = intent {
        val tests = getModelState().authorTests
        val newList = testHelper.toggleTestLike(position, tests)
        updateModelState {
            copy(authorTests = newList)
        }
    }

    fun openTestPassingScreen() = intent {
        val screen = NavigationScreen.Tests.Passing(testId)
        router.navigateTo(screen)
    }

    fun openCodeScreen() = intent {
        val screen = NavigationScreen.Tests.ShareCode(testId)
        router.navigateTo(screen)
    }

    private fun loadData() = intent {
        val test = getTest(testId)
        val testsPage = getTests(
            limit = TESTS_PAGE_SIZE,
            offset = 0,
            getType = TestGetType.MAIN,
            userId = test.creator.id
        )

        updateModelState {
            copy(
                loadingState = TestPreviewModelState.LoadingState.IDLE,
                test = test,
                authorTests = testsPage.tests
            )
        }
    }
    private companion object {
        const val TESTS_PAGE_SIZE = 3
    }

}
