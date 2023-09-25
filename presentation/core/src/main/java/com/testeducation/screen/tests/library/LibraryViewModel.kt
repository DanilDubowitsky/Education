package com.testeducation.screen.tests.library

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.test.GetTests
import com.testeducation.domain.model.test.TestGetType
import com.testeducation.domain.model.test.TestSettings
import com.testeducation.domain.model.test.TestShort
import com.testeducation.domain.model.test.TestStyle
import com.testeducation.domain.model.theme.ThemeShort
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.model.test.TestGetTypeUI
import com.testeducation.logic.screen.tests.library.LibrarySideEffect
import com.testeducation.logic.screen.tests.library.LibraryState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import kotlinx.coroutines.async
import org.orbitmvi.orbit.syntax.simple.intent

class LibraryViewModel(
    private val router: NavigationRouter,
    private val getTests: GetTests,
    reducer: IReducer<LibraryModelState, LibraryState>,
    exceptionHandler: IExceptionHandler
) : BaseViewModel<LibraryModelState, LibraryState, LibrarySideEffect>(
    reducer,
    exceptionHandler
) {

    override val initialModelState: LibraryModelState = LibraryModelState()

    init {
        loadData()
    }

    fun openPublishedTests() = intent {
        navigateToTestsLibrary(TestGetTypeUI.CREATED)
    }

    fun openPassedTests() = intent {
        navigateToTestsLibrary(TestGetTypeUI.PASSED)
    }

    // TODO: use draft type when ready
    fun openDraftTests() = intent {
        navigateToTestsLibrary(TestGetTypeUI.MAIN)
    }

    private fun navigateToTestsLibrary(type: TestGetTypeUI) {
        val screen = NavigationScreen.Tests.Library(type)
        router.navigateTo(screen)
    }

    private fun loadData() = intent {
        launchJob {
            val publishedTestsDeferred = async {
                getTests(
                    limit = TEST_LIBRARY_LIMIT,
                    getType = TestGetType.CREATED,
                    offset = 0
                )
            }

            val passedTestsDeferred = async {
                getTests(
                    limit = TEST_LIBRARY_LIMIT,
                    getType = TestGetType.CREATED,
                    offset = 0
                )
            }

            val draftsTestsDeferred = async {
                getTests(
                    limit = TEST_LIBRARY_LIMIT,
                    getType = TestGetType.CREATED,
                    offset = 0
                )
            }
            val publishedTests = publishedTestsDeferred.await()
            val passedTests = passedTestsDeferred.await()
            val draftsTests = draftsTestsDeferred.await()

            updateModelState {
                copy(
                    publishedTests = publishedTests.tests,
                    passedTests = passedTests.tests,
                    draftsTests = draftsTests.tests,
                    loadingState = LibraryModelState.LoadingState.IDLE
                )
            }
        }
    }

    private companion object {
        const val TEST_LIBRARY_LIMIT = 5
    }

}
