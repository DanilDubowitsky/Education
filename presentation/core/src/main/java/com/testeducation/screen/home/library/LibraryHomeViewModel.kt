package com.testeducation.screen.home.library

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.home.library.LibraryHomeSideEffect
import com.testeducation.logic.screen.home.library.LibraryHomeState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import org.orbitmvi.orbit.syntax.simple.intent

class LibraryHomeViewModel(
    reducer: IReducer<LibraryHomeModelState, LibraryHomeState>,
    exceptionHandler: IExceptionHandler,
    private val router: NavigationRouter
) : BaseViewModel<LibraryHomeModelState, LibraryHomeState, LibraryHomeSideEffect>(
    reducer,
    exceptionHandler
) {

    override val initialModelState: LibraryHomeModelState = LibraryHomeModelState()

    fun navigateToLibrary() = intent {
        router.replace(NavigationScreen.Main.Library, key = LIBRARY_NAVIGATOR_KEY)
        updateModelState {
            copy(currentScreen = LibraryHomeModelState.Screen.TEST_LIBRARY)
        }
    }

    companion object {
        const val LIBRARY_NAVIGATOR_KEY = "LIBRARY_NAVIGATOR"
    }

    override fun onCleared() {
        println("LIBRARY_ON_CLEARED")
        super.onCleared()
    }

}
