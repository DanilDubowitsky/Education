package com.example.education.di.modules.fragment.home.tests

import androidx.lifecycle.ViewModel
import com.example.core.IReducer
import com.example.domain.cases.test.GetTests
import com.example.education.di.viewmodel.ViewModelKey
import com.example.helper.error.IExceptionHandler
import com.example.logic.screen.tests.TestsState
import com.example.navigation.core.NavigationRouter
import com.example.screen.tests.TestsModelState
import com.example.screen.tests.TestsReducer
import com.example.screen.tests.TestsViewModel
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
            reducer: IReducer<TestsModelState, TestsState>,
            exceptionHandler: IExceptionHandler
        ): TestsViewModel = TestsViewModel(
            router,
            getTests,
            reducer,
            exceptionHandler
        )
    }
}