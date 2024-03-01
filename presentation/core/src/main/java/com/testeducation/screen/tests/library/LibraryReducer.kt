package com.testeducation.screen.tests.library

import com.testeducation.converter.test.toUIModels
import com.testeducation.core.IReducer
import com.testeducation.logic.screen.tests.library.LibraryState

class LibraryReducer : IReducer<LibraryModelState, LibraryState> {

    override fun reduce(modelState: LibraryModelState): LibraryState = modelState.run {

        LibraryState(
            isLoading = loadingState == LibraryModelState.LoadingState.LOADING,
            draftsTests = draftsTests.toUIModels(),
            publishedTests = publishedTests.toUIModels(),
            passedTests = passedTests.toUIModels(),
            isRefreshing = isRefreshing
        )
    }
}
