package com.testeducation.education.di.modules.screen.home.library

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.home.library.LibraryHomeState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.screen.home.library.LibraryHomeModelState
import com.testeducation.screen.home.library.LibraryHomeReducer
import com.testeducation.screen.home.library.LibraryHomeViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface HomeLibraryModule {

    @Binds
    @IntoMap
    @ViewModelKey(LibraryHomeViewModel::class)
    fun bindViewModel(viewModel: LibraryHomeViewModel): ViewModel

    companion object {
        @Provides
        fun provideReducer(): IReducer<LibraryHomeModelState, LibraryHomeState> =
            LibraryHomeReducer()

        @Provides
        fun provideViewModel(
            router: NavigationRouter,
            reducer: IReducer<LibraryHomeModelState, LibraryHomeState>,
            exceptionHandler: IExceptionHandler
        ): LibraryHomeViewModel {
            return LibraryHomeViewModel(
                reducer,
                exceptionHandler,
                router
            )

        }
    }
}
