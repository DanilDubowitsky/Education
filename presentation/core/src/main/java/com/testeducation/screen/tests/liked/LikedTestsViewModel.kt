package com.testeducation.screen.tests.liked

import com.testeducation.converter.test.toModels
import com.testeducation.converter.test.toUIModel
import com.testeducation.converter.test.toUIModels
import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.test.GetTests
import com.testeducation.domain.cases.theme.GetThemes
import com.testeducation.domain.model.global.OrderDirection
import com.testeducation.domain.model.test.TestGetType
import com.testeducation.domain.model.test.TestOrderField
import com.testeducation.domain.model.theme.ThemeShort
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.test.ITestHelper
import com.testeducation.logic.model.test.TestFiltersUI
import com.testeducation.logic.model.test.TestGetTypeUI
import com.testeducation.logic.screen.tests.liked.LikedTestsSideEffect
import com.testeducation.logic.screen.tests.liked.LikedTestsState
import com.testeducation.navigation.core.Disposable
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.screen.tests.base.TestsDefaults
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect

class LikedTestsViewModel(
    private val router: NavigationRouter,
    private val testShortHelper: ITestHelper,
    private val getThemes: GetThemes,
    private val getLikedTests: GetTests,
    reducer: IReducer<LikedTestsModelState, LikedTestsState>,
    exceptionHandler: IExceptionHandler
) : BaseViewModel<LikedTestsModelState, LikedTestsState, LikedTestsSideEffect>(
    reducer,
    exceptionHandler
) {

    override val initialModelState: LikedTestsModelState = LikedTestsModelState()

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
            modelState.tests,
            removeFromList = true
        )
        updateModelState {
            copy(tests = newTests)
        }
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
                testsLoadingState = LikedTestsModelState.TestsLoadingState.LOADING
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
                TestGetTypeUI.LIKED
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
                testsLoadingState = LikedTestsModelState.TestsLoadingState.NEXT_PAGE
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

    fun openTestPreview(id: String) = intent {
        val screen = NavigationScreen.Tests.Preview(id)
        router.navigateTo(screen)
    }

    fun refreshContent() = intent {
        val modelState = getModelState()
        val loadingState = if (modelState.tests.isEmpty()) {
            LikedTestsModelState.TestsLoadingState.IDLE
        } else LikedTestsModelState.TestsLoadingState.LOADING
        updateModelState {
            copy(
                tests = emptyList(),
                testsLoadingState = loadingState
            )
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
                testsLoadingState = LikedTestsModelState.TestsLoadingState.LOADING
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
            copy(themes = themes, themesLoadingState = LikedTestsModelState.ThemeLoadingState.IDLE)
        }
    }

    private fun loadThemes() = intent {
        getThemes().collect(::handleThemes)
    }

    private fun loadTests() = singleIntent(getLikedTests.javaClass.name) {
        val modelState = getModelState()
        val testsPage = modelState.run {
            getLikedTests(
                themeId = selectedThemeId,
                orderField = selectedOrderField,
                minTime = timeLimitFrom.toIntOrNull(),
                maxTime = timeLimitTo.toIntOrNull(),
                hasLimit = isTimeLimited,
                maxQuestions = questionsLimitTo.toIntOrNull(),
                minQuestions = questionsLimitFrom.toIntOrNull(),
                limit = TestsDefaults.TESTS_PAGE_SIZE,
                offset = tests.size,
                getType = TestGetType.LIKED,
                orderDirection = selectedDirection
            )
        }

        updateModelState {
            copy(
                tests = tests + testsPage.tests,
                testsLoadingState = LikedTestsModelState.TestsLoadingState.IDLE,
                totalTestsCount = testsPage.itemsTotal
            )
        }
        postSideEffect(LikedTestsSideEffect.OnLoadReady)
    }
}