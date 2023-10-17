package com.testeducation.education.di.modules.screen.home.library

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.test.GetTests
import com.testeducation.domain.cases.test.ToggleTestLike
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.test.ITestHelper
import com.testeducation.helper.test.TestHelper
import com.testeducation.logic.screen.tests.library.LibraryState
import com.testeducation.navigation.core.NavigationRouter
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
        fun provideTestHelper(toggleTestLike: ToggleTestLike): ITestHelper =
            TestHelper(toggleTestLike)

        @Provides
        fun provideReducer(): IReducer<LibraryModelState, LibraryState> =
            LibraryReducer()

        @Provides
        fun provideViewModel(
            router: NavigationRouter,
            getTests: GetTests,
            reducer: IReducer<LibraryModelState, LibraryState>,
            exceptionHandler: IExceptionHandler,
            testHelper: ITestHelper,
        ): LibraryViewModel {
            return LibraryViewModel(
                reducer,
                exceptionHandler,
                router,
                testHelper,
                getTests
            )
        }
    }
}
