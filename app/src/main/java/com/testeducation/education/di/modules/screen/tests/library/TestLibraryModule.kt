package com.testeducation.education.di.modules.screen.tests.library

import androidx.lifecycle.ViewModel
import com.testeducation.converter.test.toModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.test.GetTests
import com.testeducation.domain.cases.test.ToggleTestLike
import com.testeducation.domain.cases.theme.GetThemes
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.test.ITestHelper
import com.testeducation.helper.test.TestHelper
import com.testeducation.logic.screen.tests.library.tests.TestLibraryState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.screen.tests.library.tests.TestLibraryModelState
import com.testeducation.screen.tests.library.tests.TestLibraryReducer
import com.testeducation.screen.tests.library.tests.TestLibraryViewModel
import com.testeducation.ui.screen.tests.library.test.TestLibraryFragment
import com.testeducation.ui.utils.getScreen
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
            fragment: TestLibraryFragment,
            router: NavigationRouter,
            testShortHelper: ITestHelper,
            getTests: GetTests,
            getThemes: GetThemes,
            reducer: IReducer<TestLibraryModelState, TestLibraryState>,
            exceptionHandler: IExceptionHandler
        ): TestLibraryViewModel {
            val screen = fragment.getScreen<NavigationScreen.Tests.Library>()
            val testGetType = screen.getTypeUI
            return TestLibraryViewModel(
                testGetType,
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