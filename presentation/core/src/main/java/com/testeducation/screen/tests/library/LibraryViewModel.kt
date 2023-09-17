package com.testeducation.screen.tests.library

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.test.GetTests
import com.testeducation.domain.model.test.TestGetType
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.tests.library.LibrarySideEffect
import com.testeducation.logic.screen.tests.library.LibraryState
import kotlinx.coroutines.async
import org.orbitmvi.orbit.syntax.simple.intent

class LibraryViewModel(
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
