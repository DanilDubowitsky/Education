package com.testeducation.screen.tests.filters

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.test.GetTests
import com.testeducation.domain.cases.theme.GetThemes
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.model.test.TestFiltersUI
import com.testeducation.logic.screen.tests.filters.TestsFiltersSideEffect
import com.testeducation.logic.screen.tests.filters.TestsFiltersState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
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
        timeLimitTo = filtersUI.maxTime
    )

    init {
        loadThemes()
        initData()
    }

    private fun initData() = intent {
        val sideEffect = TestsFiltersSideEffect.SetTextFilters(
            filtersUI.minQuestions.toString(),
            filtersUI.maxQuestions.toString(),
            filtersUI.maxTime.toString(),
            filtersUI.minTime.toString()
        )
        postSideEffect(sideEffect)
    }

    fun exit() {
        router.exit()
    }

    fun selectTheme(id: String) = intent {
        updateModelState {
            copy(selectedTheme = id)
        }
        loadTests()
    }

    fun onMinQuestionsCountChanged(value: String) = intent {
        updateModelState {
            copy(questionsLimitFrom = value.toInt())
        }
        loadTests()
    }

    fun onMaxQuestionsCountChanged(value: String) = intent {
        updateModelState {
            copy(questionsLimitTo = value.toInt())
        }
        loadTests()
    }

    fun onMinAnswerTimeChanged(value: String) = intent {
        updateModelState {
            copy(timeLimitFrom = value.toInt())
        }
        loadTests()
    }

    fun onMaxAnswerTimeChanged(value: String) = intent {
        updateModelState {
            copy(timeLimitTo = value.toInt())
        }
        loadTests()
    }

    private fun loadTests() = intent {
        launchSingleJob(getTests.javaClass.name) {
            delay(TESTS_LOADING_DELAY)
            updateModelState {
                copy(
                    loadingState = TestsFiltersModelState.LoadingState.LOADING
                )
            }
            val modelState = getModelState()
            val page = getTests(
                themeId = modelState.selectedTheme,
                orderField = modelState.testOrderField,
                minTime = modelState.timeLimitFrom,
                maxTime = modelState.timeLimitTo,
                hasLimit = modelState.isTimeLimited,
                maxQuestions = modelState.questionsLimitFrom,
                minQuestions = modelState.questionsLimitTo,
                limit = Int.MAX_VALUE,
                pageIndex = 0
            )
            updateModelState {
                copy(
                    filterResultCount = page.itemsTotal,
                    result = page.tests,
                    loadingState = TestsFiltersModelState.LoadingState.IDLE
                )
            }
        }

    }

    private fun loadThemes() = intent {
        val themes = getThemes()
        updateModelState {
            copy(themes = themes)
        }
    }

    private companion object {
        const val TESTS_LOADING_DELAY = 500L
    }
}