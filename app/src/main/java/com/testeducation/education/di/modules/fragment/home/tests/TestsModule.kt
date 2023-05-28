package com.testeducation.education.di.modules.fragment.home.tests

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.test.GetTests
import com.testeducation.domain.cases.theme.GetThemes
import com.testeducation.domain.cases.user.GetCurrentUser
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.tests.TestsState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.screen.tests.TestsModelState
import com.testeducation.screen.tests.TestsReducer
import com.testeducation.screen.tests.TestsViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface TestsModule {

    @Binds
    @IntoMap
    @ViewModelKey(TestsViewModel::class)
    fun bindViewModel(viewModel: TestsViewModel): ViewModel

    companion object {
        @Provides
        fun provideReducer(): IReducer<TestsModelState, TestsState> =
            TestsReducer()

        @Provides
        fun provideViewModel(
            getTests: GetTests,
            router: NavigationRouter,
            getCurrentUser: GetCurrentUser,
            getThemes: GetThemes,
            reducer: IReducer<TestsModelState, TestsState>,
            exceptionHandler: IExceptionHandler
        ): TestsViewModel = TestsViewModel(
            router,
            getTests,
            getThemes,
            getCurrentUser,
            reducer,
            exceptionHandler
        )
    }
}