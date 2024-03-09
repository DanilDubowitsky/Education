package com.testeducation.screen.tests.library

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.test.GetTests
import com.testeducation.domain.model.test.Test
import com.testeducation.domain.model.test.TestGetType
import com.testeducation.domain.model.test.TestShort
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.test.ITestHelper
import com.testeducation.logic.model.test.TestGetTypeUI
import com.testeducation.logic.model.test.TestLibraryGetTypeUI
import com.testeducation.logic.screen.tests.library.LibrarySideEffect
import com.testeducation.logic.screen.tests.library.LibraryState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.screen.home.library.LibraryHomeViewModel.Companion.LIBRARY_NAVIGATOR_KEY
import com.testeducation.utils.firstByConditionOrNull
import com.testeducation.utils.isNotEmptyOrBlank
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
        navigateToTestsLibrary(TestLibraryGetTypeUI.PUBLISHED)
    }

    fun openPassedTests() = intent {
        navigateToTestsLibrary(TestLibraryGetTypeUI.PASSED)
    }

    fun openDraftTests() = intent {
        navigateToTestsLibrary(TestLibraryGetTypeUI.DRAFT)
    }

    fun openTestPreview(id: String) = intent {
        router.setResultListener(NavigationScreen.Tests.Details.OnTestEditorUpdated) {
            refresh()
        }
        val modelState = getModelState()
        val draftTests = modelState.draftsTests
        val publishedTests = modelState.publishedTests
        val testInDraft = draftTests.firstByConditionOrNull(TestShort::id, id)
        val testInPublished = publishedTests.firstByConditionOrNull(TestShort::id, id)
        val screen = when {
            testInDraft != null -> {
                NavigationScreen.Tests.Action(testInDraft.id, testInDraft.title)
            }

            testInPublished != null -> {
                NavigationScreen.Tests.Action(testInPublished.id, testInPublished.title)
            }

            else -> {
                NavigationScreen.Tests.Preview(id)
            }
        }
        router.navigateTo(screen)
    }

    fun toggleTestLike(position: Int, type: TestGetTypeUI) = intent {
        when (type) {
            TestGetTypeUI.CONTENT,
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

            TestGetTypeUI.DRAFT -> {
                val tests = getModelState().draftsTests
                val newTests = testHelper.toggleTestLike(position, tests)
                updateModelState {
                    copy(passedTests = newTests)
                }
            }
        }
    }

    fun openConstructor() = intent {
        val screen = NavigationScreen.Main.CreationTest
        router.setResultListener(
            NavigationScreen.Main.CreationTest.OnCreationTestResult
        ) { idTest ->
            if (idTest.isNotEmptyOrBlank()) {
                val screenSelectionQuestion = NavigationScreen.Main.SelectionTest(idTest)
                router.navigateTo(screenSelectionQuestion)
            }
        }
        router.navigateTo(screen)
    }

    fun openCatalog() = intent {
        router.sendResult(
            NavigationScreen.Main.HomeLibrary.OnTestsSelected,
            Unit,
            needRemoveListener = false
        )
    }

    fun refresh() = intent {
        updateModelState {
            copy(isRefreshing = true)
        }
        loadData()
    }

    fun loadData() = intent {
        launchJob {
            updateModelState {
                copy(loadingState = LibraryModelState.LoadingState.LOADING)
            }
            val publishedTestsDeferred = async {
                getTests(
                    limit = TEST_LIBRARY_LIMIT,
                    getType = TestGetType.ACCOUNT,
                    offset = 0,
                    status = Test.Status.PUBLISHED
                )
            }

            val draftTestsDeferred = async {
                getTests(
                    limit = TEST_LIBRARY_LIMIT,
                    getType = TestGetType.ACCOUNT,
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
            val remoteDraftTests = draftTestsDeferred.await()

            updateModelState {
                copy(
                    publishedTests = publishedTests.tests,
                    passedTests = passedTests.tests,
                    draftsTests = remoteDraftTests.tests,
                    loadingState = LibraryModelState.LoadingState.IDLE,
                    totalTests = publishedTests.tests + passedTests.tests + remoteDraftTests.tests,
                    isRefreshing = false
                )
            }
        }
    }

    private fun navigateToTestsLibrary(type: TestLibraryGetTypeUI) {
        val screen = NavigationScreen.Tests.Library(type)
        router.navigateTo(screen, key = LIBRARY_NAVIGATOR_KEY)
    }

    private companion object {
        const val TEST_LIBRARY_LIMIT = 5
    }

}
