package com.testeducation.screen.tests.filters

import com.testeducation.converter.test.toModel
import com.testeducation.converter.test.toModels
import com.testeducation.converter.test.toUIModel
import com.testeducation.converter.test.toUIModels
import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.test.GetTests
import com.testeducation.domain.cases.theme.GetThemes
import com.testeducation.domain.model.test.Test
import com.testeducation.domain.model.test.TestGetType
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.model.test.TestFiltersUI
import com.testeducation.logic.model.test.TestGetTypeUI
import com.testeducation.logic.screen.tests.filters.TestsFiltersSideEffect
import com.testeducation.logic.screen.tests.filters.TestsFiltersState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.screen.tests.base.TestsDefaults
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect

class TestsFiltersViewModel(
    private val filtersUI: TestFiltersUI,
    private val router: NavigationRouter,
    private val getThemes: GetThemes,
    private val getTests: GetTests,
    reducer: IReducer<TestsFiltersModelState, TestsFiltersState>,
    exceptionHandler: IExceptionHandler
) : BaseViewModel<TestsFiltersModelState, TestsFiltersState, TestsFiltersSideEffect>(
    reducer,
    exceptionHandler
) {

    override val initialModelState: TestsFiltersModelState = TestsFiltersModelState(
        selectedTheme = filtersUI.selectedTheme,
        isTimeLimited = filtersUI.hasLimit,
        questionsLimitFrom = filtersUI.minQuestions,
        questionsLimitTo = filtersUI.maxQuestions,
        timeLimitFrom = filtersUI.minTime,
        timeLimitTo = filtersUI.maxTime,
        testOrderField = filtersUI.orderFieldUI.toModel(),
        filterResultCount = filtersUI.currentItemsCount,
        testGetTypeUI = filtersUI.testGetTypeUI,
        result = filtersUI.preLoadedTests.toModels()
    )

    init {
        loadThemes()
        initData()
    }

    private fun initData() = intent {
        val sideEffect = TestsFiltersSideEffect.SetTextFilters(
            filtersUI.minQuestions,
            filtersUI.maxQuestions,
            filtersUI.maxTime,
            filtersUI.minTime
        )
        postSideEffect(sideEffect)
    }

    fun exit() {
        router.exit()
    }

    fun setLimited() = intent {
        updateModelState {
            copy(
                isTimeLimited = true
            )
        }
    }

    fun setUnlimited() = intent {
        updateModelState {
            copy(
                isTimeLimited = false
            )
        }
    }

    fun selectTheme(id: String) = intent {
        updateModelState {
            copy(selectedTheme = id)
        }
        loadTests()
    }

    fun onMinQuestionsCountChanged(value: String) = singleIntent(getTests.javaClass.name) {
        updateModelState {
            copy(loadingState = TestsFiltersModelState.LoadingState.LOADING)
        }
        delay(TESTS_LOADING_DELAY)
        updateModelState {
            copy(questionsLimitFrom = value)
        }
        loadTests()
    }

    fun onMaxQuestionsCountChanged(value: String) = singleIntent(getTests.javaClass.name) {
        updateModelState {
            copy(loadingState = TestsFiltersModelState.LoadingState.LOADING)
        }
        delay(TESTS_LOADING_DELAY)
        updateModelState {
            copy(questionsLimitTo = value)
        }
        loadTests()
    }

    fun onMinAnswerTimeChanged(value: String) = singleIntent(getTests.javaClass.name) {
        updateModelState {
            copy(loadingState = TestsFiltersModelState.LoadingState.LOADING)
        }
        delay(TESTS_LOADING_DELAY)
        updateModelState {
            copy(timeLimitFrom = value)
        }
        loadTests()
    }

    fun onMaxAnswerTimeChanged(value: String) = singleIntent(getTests.javaClass.name) {
        updateModelState {
            copy(loadingState = TestsFiltersModelState.LoadingState.LOADING)
        }
        delay(TESTS_LOADING_DELAY)
        updateModelState {
            copy(timeLimitTo = value)
        }
        loadTests()
    }

    fun showResults() = intent {
        getModelState().run {
            val filters = TestFiltersUI(
                minTime = timeLimitFrom,
                maxTime = timeLimitTo,
                minQuestions = questionsLimitFrom,
                maxQuestions = questionsLimitTo,
                hasLimit = isTimeLimited,
                orderFieldUI = testOrderField?.toUIModel()!!,
                preLoadedTests = result.toUIModels(),
                selectedTheme = selectedTheme,
                currentItemsCount = filterResultCount ?: 0,
                testGetTypeUI = testGetTypeUI
            )
            router.sendResult(NavigationScreen.Tests.Filters.OnFiltersChanged, filters)
            router.exit()
        }
    }

    fun resetFilters() = intent {
        val previousState = getModelState()
        updateModelState {
            copy(
                selectedTheme = null,
                questionsLimitFrom = TestsDefaults.DEFAULT_QUESTIONS_MIN,
                questionsLimitTo = TestsDefaults.DEFAULT_QUESTIONS_MAX,
                timeLimitFrom = TestsDefaults.DEFAULT_TIME_MIN,
                timeLimitTo = TestsDefaults.DEFAULT_TIME_MAX,
                isTimeLimited = TestsDefaults.DEFAULT_HAS_LIMIT
            )
        }
        val currentState = getModelState()
        if (previousState == currentState) return@intent
        val sideEffect = TestsFiltersSideEffect.SetTextFilters(
            TestsDefaults.DEFAULT_QUESTIONS_MIN,
            TestsDefaults.DEFAULT_QUESTIONS_MAX,
            TestsDefaults.DEFAULT_TIME_MAX,
            TestsDefaults.DEFAULT_TIME_MIN
        )
        postSideEffect(sideEffect)
        loadTests()
    }

    private fun loadTests() = singleIntent(getTests.javaClass.name) {
        updateModelState {
            copy(
                loadingState = TestsFiltersModelState.LoadingState.LOADING
            )
        }
        val modelState = getModelState()
        val (status, getType) = when (filtersUI.testGetTypeUI) {
            TestGetTypeUI.CONTENT -> Test.Status.PUBLISHED to TestGetType.CONTENT
            TestGetTypeUI.LIKED -> Test.Status.PUBLISHED to TestGetType.LIKED
            TestGetTypeUI.CREATED -> Test.Status.PUBLISHED to TestGetType.ACCOUNT
            TestGetTypeUI.PASSED -> Test.Status.PUBLISHED to TestGetType.PASSED
            TestGetTypeUI.DRAFT -> Test.Status.DRAFT to TestGetType.ACCOUNT
        }

        val page = getTests(
            themeId = modelState.selectedTheme,
            orderField = modelState.testOrderField,
            minTime = modelState.timeLimitFrom.toIntOrNull(),
            maxTime = modelState.timeLimitTo.toIntOrNull(),
            hasLimit = modelState.isTimeLimited,
            maxQuestions = modelState.questionsLimitTo.toIntOrNull(),
            minQuestions = modelState.questionsLimitFrom.toIntOrNull(),
            limit = TestsDefaults.TESTS_PAGE_SIZE,
            offset = 0,
            getType = getType,
            status = status
        )

        updateModelState {
            copy(
                filterResultCount = page.itemsTotal,
                result = page.tests,
                loadingState = TestsFiltersModelState.LoadingState.IDLE
            )
        }
    }

    private fun loadThemes() = intent {
        getThemes().collect { themes ->
            updateModelState {
                copy(themes = themes)
            }
        }
    }

    private companion object {
        const val TESTS_LOADING_DELAY = 600L
    }
}