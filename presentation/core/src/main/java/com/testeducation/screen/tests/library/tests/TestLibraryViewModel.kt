package com.testeducation.screen.tests.library.tests

import com.testeducation.converter.test.toModels
import com.testeducation.converter.test.toUI
import com.testeducation.converter.test.toUIModel
import com.testeducation.converter.test.toUIModels
import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.test.GetTests
import com.testeducation.domain.cases.theme.GetThemes
import com.testeducation.domain.model.test.TestGetType
import com.testeducation.domain.model.theme.ThemeShort
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.test.ITestHelper
import com.testeducation.logic.model.test.TestFiltersUI
import com.testeducation.logic.model.test.TestGetTypeUI
import com.testeducation.logic.screen.tests.library.tests.TestLibrarySideEffect
import com.testeducation.logic.screen.tests.library.tests.TestLibraryState
import com.testeducation.navigation.core.Disposable
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.screen.tests.base.TestsDefaults
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect

class TestLibraryViewModel(
    private val testGetType: TestGetType,
    private val testShortHelper: ITestHelper,
    private val router: NavigationRouter,
    private val getTests: GetTests,
    private val getThemes: GetThemes,
    reducer: IReducer<TestLibraryModelState, TestLibraryState>,
    exceptionHandler: IExceptionHandler
) : BaseViewModel<TestLibraryModelState, TestLibraryState, TestLibrarySideEffect>(
    reducer,
    exceptionHandler
) {

    override val initialModelState: TestLibraryModelState = TestLibraryModelState()

    @Volatile
    private var resultDisposable: Disposable? = null

    init {
        onScrollToBottom()
        loadTests()
        loadThemes()
    }

    fun toggleTestLike(position: Int) = intent {
        val modelState = getModelState()
        val newTests = testShortHelper.toggleTestLike(
            position,
            modelState.tests
        )
        updateModelState {
            copy(tests = newTests)
        }
    }

    fun onThemeChanged(themeId: String) = intent {
        updateModelState {
            copy(
                selectedThemeId = themeId,
                tests = emptyList(),
                testsLoadingState = TestLibraryModelState.TestsLoadingState.LOADING
            )
        }
        loadTests()
    }

    fun openFilters() = intent {
        val filters = getModelState().run {
            TestFiltersUI(
                timeLimitFrom,
                timeLimitTo,
                isTimeLimited,
                questionsLimitFrom,
                questionsLimitTo,
                selectedThemeId,
                selectedOrderField.toUIModel(),
                tests.toUIModels(),
                tests.size,
                testGetType.toUI()
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
        updateModelState {
            copy(
                testsLoadingState = TestLibraryModelState.TestsLoadingState.NEXT_PAGE
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

    private fun handleNewFilters(newFilters: TestFiltersUI) = intent {
        updateModelState {
            copy(
                isTimeLimited = newFilters.hasLimit,
                timeLimitFrom = newFilters.minTime,
                timeLimitTo = newFilters.maxTime,
                questionsLimitTo = newFilters.maxQuestions,
                questionsLimitFrom = newFilters.minQuestions,
                selectedThemeId = newFilters.selectedTheme,
                tests = newFilters.preLoadedTests.toModels()
            )
        }
    }

    private fun handleThemes(themes: List<ThemeShort>) = intent {
        updateModelState {
            copy(
                themes = themes,
                themesLoadingState = TestLibraryModelState.ThemeLoadingState.IDLE
            )
        }
    }

    private fun loadThemes() = intent {
        getThemes().collect(::handleThemes)
    }

    private fun loadTests() = singleIntent(getTests.javaClass.name) {
        val modelState = getModelState()
        val testsPage = modelState.run {
            getTests(
                themeId = selectedThemeId,
                orderField = selectedOrderField,
                minTime = timeLimitFrom.toIntOrNull(),
                maxTime = timeLimitTo.toIntOrNull(),
                hasLimit = isTimeLimited,
                maxQuestions = questionsLimitTo.toIntOrNull(),
                minQuestions = questionsLimitFrom.toIntOrNull(),
                limit = TestsDefaults.TESTS_PAGE_SIZE,
                offset = tests.size,
                getType = testGetType
            )
        }

        updateModelState {
            copy(
                tests = tests + testsPage.tests,
                testsLoadingState = TestLibraryModelState.TestsLoadingState.IDLE,
                totalTestsCount = testsPage.itemsTotal
            )
        }
        postSideEffect(TestLibrarySideEffect.OnLoadReady)
    }

}