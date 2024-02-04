package com.testeducation.screen.tests.library.tests

import com.testeducation.converter.test.toModels
import com.testeducation.converter.test.toUIModel
import com.testeducation.converter.test.toUIModels
import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.test.GetTests
import com.testeducation.domain.cases.theme.GetThemes
import com.testeducation.domain.model.global.OrderDirection
import com.testeducation.domain.model.test.Test
import com.testeducation.domain.model.test.TestGetType
import com.testeducation.domain.model.test.TestOrderField
import com.testeducation.domain.model.theme.ThemeShort
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.test.ITestHelper
import com.testeducation.logic.model.test.TestFiltersUI
import com.testeducation.logic.model.test.TestGetTypeUI
import com.testeducation.logic.model.test.TestLibraryGetTypeUI
import com.testeducation.logic.screen.tests.library.tests.TestLibrarySideEffect
import com.testeducation.logic.screen.tests.library.tests.TestLibraryState
import com.testeducation.navigation.core.Disposable
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.screen.home.library.LibraryHomeViewModel.Companion.LIBRARY_NAVIGATOR_KEY
import com.testeducation.screen.tests.base.TestsDefaults
import com.testeducation.screen.tests.liked.LikedTestsModelState
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect

class TestLibraryViewModel(
    private val testGetType: TestLibraryGetTypeUI,
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

    fun openTestPreview(id: String) = intent {
        val screen = NavigationScreen.Tests.Preview(id)
        router.navigateTo(screen)
    }

    fun onThemeChanged(themeId: String) = intent {
        val modelState = getModelState()
        var selectedTheme: String? = themeId
        if (modelState.selectedThemeId == selectedTheme) {
            selectedTheme = null
        }
        updateModelState {
            copy(
                selectedThemeId = selectedTheme,
                tests = emptyList(),
                testsLoadingState = TestLibraryModelState.TestsLoadingState.LOADING
            )
        }
        loadTests()
    }

    fun openFilters() = intent {
        val getType = when (testGetType) {
            TestLibraryGetTypeUI.PUBLISHED -> TestGetTypeUI.CONTENT
            TestLibraryGetTypeUI.PASSED -> TestGetTypeUI.PASSED
            TestLibraryGetTypeUI.DRAFT -> TestGetTypeUI.DRAFT
        }
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
                getType
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

    fun exit() = intent {
        router.exit(LIBRARY_NAVIGATOR_KEY)
    }

    fun refresh() = intent {
        updateModelState {
            copy(tests = emptyList())
        }
        loadTests()
    }

    fun openSort() = intent {
        val modelState = getModelState()
        val screen = NavigationScreen.Tests.TestSort(
            modelState.selectedOrderField.name,
            modelState.selectedDirection.name
        )
        router.setResultListener(NavigationScreen.Tests.TestSort.OnSortChanged, ::handleNewSort)
        router.navigateTo(screen)
    }

    private fun handleNewSort(
        values: NavigationScreen.Tests.TestSort.SortValues
    ) = intent {
        updateModelState {
            copy(
                selectedOrderField = TestOrderField.valueOf(values.orderField),
                selectedDirection = OrderDirection.valueOf(values.direction),
                tests = emptyList(),
                testsLoadingState = TestLibraryModelState.TestsLoadingState.LOADING
            )
        }
        loadTests()
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
        val (status, getType) = when (testGetType) {
            TestLibraryGetTypeUI.PUBLISHED -> Test.Status.PUBLISHED to TestGetType.ACCOUNT
            TestLibraryGetTypeUI.PASSED -> Test.Status.PUBLISHED to TestGetType.PASSED
            TestLibraryGetTypeUI.DRAFT -> Test.Status.DRAFT to TestGetType.ACCOUNT
        }
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
                getType = getType,
                status = status,
                orderDirection = selectedDirection
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