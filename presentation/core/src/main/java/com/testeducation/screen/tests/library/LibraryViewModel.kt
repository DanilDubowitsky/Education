package com.testeducation.screen.tests.library

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.test.GetTests
import com.testeducation.domain.model.test.Test
import com.testeducation.domain.model.test.TestGetType
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.test.ITestHelper
import com.testeducation.logic.model.test.TestGetTypeUI
import com.testeducation.logic.screen.tests.library.LibrarySideEffect
import com.testeducation.logic.screen.tests.library.LibraryState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.screen.home.HomeViewModel.Companion.HOME_NAVIGATOR_KEY
import kotlinx.coroutines.async
import org.orbitmvi.orbit.syntax.simple.intent

class LibraryViewModel(
    reducer: IReducer<LibraryModelState, LibraryState>,
    exceptionHandler: IExceptionHandler,
    private val router: NavigationRouter,
    private val testHelper: ITestHelper,
    private val getTests: GetTests,
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

    fun openTestPreview(id: String) {
        val screen = NavigationScreen.Tests.Preview(id)
        router.navigateTo(screen)
    }

    fun toggleTestLike(position: Int, type: TestGetTypeUI) = intent {
        when (type) {
            TestGetTypeUI.MAIN,
            TestGetTypeUI.LIKED -> return@intent

            TestGetTypeUI.CREATED -> {
                val tests = getModelState().publishedTests
                val newTests = testHelper.toggleTestLike(position, tests)
                updateModelState {
                    copy(publishedTests = newTests)
                }
            }

            TestGetTypeUI.PASSED -> {
                val tests = getModelState().passedTests
                val newTests = testHelper.toggleTestLike(position, tests)
                updateModelState {
                    copy(passedTests = newTests)
                }
            }
        }
    }

    private fun navigateToTestsLibrary(type: TestGetTypeUI) {
        val screen = NavigationScreen.Tests.Library(type)
        router.replace(screen, key = HOME_NAVIGATOR_KEY)
    }

    private fun loadData() = intent {
        launchJob {
            val publishedTestsDeferred = async {
                getTests(
                    limit = TEST_LIBRARY_LIMIT,
                    getType = TestGetType.CREATED,
                    offset = 0,
                    status = Test.Status.PUBLISHED
                )
            }

            val draftTestsDeferred = async {
                getTests(
                    limit = TEST_LIBRARY_LIMIT,
                    getType = TestGetType.CREATED,
                    offset = 0,
                    status = Test.Status.DRAFT
                )
            }

            val passedTestsDeferred = async {
                getTests(
                    limit = TEST_LIBRARY_LIMIT,
                    getType = TestGetType.PASSED,
                    offset = 0
                )
            }

            val publishedTests = publishedTestsDeferred.await()
            val passedTests = passedTestsDeferred.await()
            val draftTests = draftTestsDeferred.await()

            updateModelState {
                copy(
                    publishedTests = publishedTests.tests,
                    passedTests = passedTests.tests,
                    draftsTests = draftTests.tests,
                    loadingState = LibraryModelState.LoadingState.IDLE
                )
            }
        }
    }

    private companion object {
        const val TEST_LIBRARY_LIMIT = 5
    }

}
