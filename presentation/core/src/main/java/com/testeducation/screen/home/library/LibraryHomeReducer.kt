package com.testeducation.screen.home.library

import com.testeducation.core.IReducer
import com.testeducation.logic.screen.home.library.LibraryHomeState

class LibraryHomeReducer : IReducer<LibraryHomeModelState, LibraryHomeState> {

    override fun reduce(modelState: LibraryHomeModelState): LibraryHomeState {
        return LibraryHomeState
    }
}
