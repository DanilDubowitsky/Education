package com.testeducation.education.di.modules.screen.home.library

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.test.GetTests
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.tests.library.LibraryState
import com.testeducation.screen.tests.library.LibraryModelState
import com.testeducation.screen.tests.library.LibraryReducer
import com.testeducation.screen.tests.library.LibraryViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface LibraryModule {

    @Binds
    @IntoMap
    @ViewModelKey(LibraryViewModel::class)
    fun bindViewModel(viewModel: LibraryViewModel): ViewModel

    companion object {
        @Provides
        fun provideReducer(): IReducer<LibraryModelState, LibraryState> =
            LibraryReducer()

        @Provides
        fun provideViewModel(
            getTests: GetTests,
            reducer: IReducer<LibraryModelState, LibraryState>,
            exceptionHandler: IExceptionHandler
        ): LibraryViewModel {
            return LibraryViewModel(getTests, reducer, exceptionHandler)
        }
    }
}
