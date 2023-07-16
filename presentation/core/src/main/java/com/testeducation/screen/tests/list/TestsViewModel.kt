package com.testeducation.screen.tests.list

import com.testeducation.converter.test.toModels
import com.testeducation.converter.test.toUIModel
import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.test.GetTests
import com.testeducation.domain.cases.theme.GetThemes
import com.testeducation.domain.cases.user.GetCurrentUser
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.test.ITestHelper
import com.testeducation.logic.model.test.TestFiltersUI
import com.testeducation.logic.screen.tests.list.TestsSideEffect
import com.testeducation.logic.screen.tests.list.TestsState
import com.testeducation.navigation.core.Disposable
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect

class TestsViewModel(
    private val router: NavigationRouter,
    private val getTests: GetTests,
    private val getThemes: GetThemes,
    private val getCurrentUser: GetCurrentUser,
    private val testHelper: ITestHelper,
    reducer: IReducer<TestsModelState, TestsState>,
    errorHandler: IExceptionHandler
) : BaseViewModel<TestsModelState, TestsState, TestsSideEffect>(reducer, errorHandler) {

    override val initialModelState: TestsModelState = TestsModelState()

    @Volatile
    private var resultDisposable: Disposable? = null

    init {
        loadTests()
        loadThemes()
        loadUserData()
    }

    fun onThemeChanged(id: String?) = intent {
        val modelState = getModelState()
        if (modelState.selectedThemeId == id) return@intent
        updateModelState {
            copy(
                tests = emptyList(),
                selectedThemeId = id,
                testsLoadingState = TestsModelState.TestsLoadingState.LOADING
            )
        }
        loadTests()
    }

    fun onScrollToBottom() = intent {
        router.sendResult(NavigationScreen.Main.Tests.OnScrollToBottom, Unit, false)
    }

    fun onScrollToTop() = intent {
        router.sendResult(NavigationScreen.Main.Tests.OnScrollToTop, Unit, false)
    }

    fun openFiltersScreen() = intent {
        val filters = getModelState().run {
            TestFiltersUI(
                timeLimitFrom,
                timeLimitTo,
                isTimeLimited,
                questionsLimitFrom,
                questionsLimitTo,
                selectedThemeId,
                selectedOrderField.toUIModel(),
                emptyList(),
                tests.size
            )
        }

        resultDisposable = router.setResultListener(
            NavigationScreen.Tests.Filters.OnFiltersChanged,
            ::handleNewFilters
        )

        val screen = NavigationScreen.Tests.Filters(
            filters
        )
        router.navigateTo(screen)
    }

    fun loadNextPage() = intent {
        val modelState = getModelState()
        if (modelState.tests.size >= modelState.totalTestsCount) return@intent
        postSideEffect(TestsSideEffect.RemoveScrollListener)
        updateModelState {
            copy(testsLoadingState = TestsModelState.TestsLoadingState.NEXT_PAGE)
        }
        loadTests()
    }

    fun toggleTestLike(position: Int) = intent {
        val modelState = getModelState()
        val newTests = testHelper.toggleTestLike(position, modelState.tests)
        updateModelState {
            copy(tests = newTests)
        }
    }

    private fun loadTests() = singleIntent(getTests.javaClass.name) {
        val modelState = getModelState()
        val page = getTests(
            themeId = modelState.selectedThemeId,
            orderField = modelState.selectedOrderField,
            minTime = modelState.timeLimitFrom.toIntOrNull(),
            maxTime = modelState.timeLimitTo.toIntOrNull(),
            hasLimit = modelState.isTimeLimited,
            maxQuestions = modelState.questionsLimitTo.toIntOrNull(),
            minQuestions = modelState.questionsLimitFrom.toIntOrNull(),
            limit = PAGE_SIZE,
            pageIndex = modelState.pageIndex
        )
        updateModelState {
            copy(
                tests = modelState.tests + page.tests,
                testsLoadingState = TestsModelState.TestsLoadingState.IDLE,
                pageIndex = page.pageIndex + 1,
                totalTestsCount = page.itemsTotal
            )
        }
        postSideEffect(TestsSideEffect.AddScrollListener)
    }

    private fun loadThemes() = intent {
        getThemes().collect { themes ->
            updateModelState {
                copy(
                    themes = themes,
                    themesLoadingState = TestsModelState.ThemesLoadingState.IDLE
                )
            }
        }
    }

    private fun loadUserData() = intent {
        val user = getCurrentUser()
        updateModelState {
            copy(
                user = user,
                profileLoadingState = TestsModelState.ProfileLoadingState.IDLE
            )
        }
    }

    private fun handleNewFilters(newFilters: TestFiltersUI) = intent {
        updateModelState {
            copy(
                isTimeLimited = newFilters.hasLimit,
                timeLimitFrom = newFilters.minTime,
                timeLimitTo = newFilters.maxTime,
                questionsLimitTo = newFilters.maxQuestions,
                questionsLimitFrom = newFilters.minQuestions,
                selectedThemeId = newFilters.selectedTheme,
                tests = newFilters.preLoadedTests.toModels(),
                pageIndex = 0
            )
        }
    }

    override fun onCleared() {
        resultDisposable?.dispose()
        super.onCleared()
    }

    companion object {
        private const val PAGE_SIZE = 10
        const val DEFAULT_QUESTIONS_MIN = "1"
        const val DEFAULT_QUESTIONS_MAX = "50"
        const val DEFAULT_TIME_MIN = "1"
        const val DEFAULT_TIME_MAX = "60"
        const val DEFAULT_HAS_LIMIT = false
    }

}
