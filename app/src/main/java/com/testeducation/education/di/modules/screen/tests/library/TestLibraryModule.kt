package com.testeducation.education.di.modules.screen.tests.library

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.test.GetTests
import com.testeducation.domain.cases.test.ToggleTestLike
import com.testeducation.domain.cases.theme.GetThemes
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.test.ITestHelper
import com.testeducation.helper.test.TestHelper
import com.testeducation.logic.screen.tests.library.TestLibraryState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.screen.tests.library.TestLibraryModelState
import com.testeducation.screen.tests.library.TestLibraryReducer
import com.testeducation.screen.tests.library.TestLibraryViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface TestLibraryModule {

    @Binds
    @IntoMap
    @ViewModelKey(TestLibraryViewModel::class)
    fun bindViewModel(viewModel: TestLibraryViewModel): ViewModel

    companion object {
        @Provides
        fun provideReducer(): IReducer<TestLibraryModelState, TestLibraryState> =
            TestLibraryReducer()

        @Provides
        fun provideTestHelper(
            toggleTestLike: ToggleTestLike
        ): ITestHelper = TestHelper(toggleTestLike)

        @Provides
        fun provideViewModel(
            router: NavigationRouter,
            testShortHelper: ITestHelper,
            getTests: GetTests,
            getThemes: GetThemes,
            reducer: IReducer<TestLibraryModelState, TestLibraryState>,
            exceptionHandler: IExceptionHandler
        ): TestLibraryViewModel {
            return TestLibraryViewModel(
                testShortHelper,
                router,
                getTests,
                getThemes,
                reducer,
                exceptionHandler
            )
        }
    }

}