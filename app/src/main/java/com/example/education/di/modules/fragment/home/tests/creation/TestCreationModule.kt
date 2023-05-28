package com.example.education.di.modules.fragment.home.tests.creation

import androidx.lifecycle.ViewModel
import com.example.core.IReducer
import com.example.domain.cases.test.GetTests
import com.example.domain.cases.theme.GetThemes
import com.example.domain.cases.user.GetCurrentUser
import com.example.education.di.viewmodel.ViewModelKey
import com.example.helper.error.IExceptionHandler
import com.example.logic.screen.tests.TestsState
import com.example.logic.screen.tests.creation.TestCreationState
import com.example.navigation.core.NavigationRouter
import com.example.screen.tests.TestsModelState
import com.example.screen.tests.TestsReducer
import com.example.screen.tests.TestsViewModel
import com.example.screen.tests.creation.TestCreationModelState
import com.example.screen.tests.creation.TestCreationReducer
import com.example.screen.tests.creation.TestCreationViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface TestCreationModule {
    @Binds
    @IntoMap
    @ViewModelKey(TestCreationViewModel::class)
    fun bindViewModel(viewModel: TestCreationViewModel): ViewModel

    companion object {
        @Provides
        fun provideReducer(): IReducer<TestCreationModelState, TestCreationState> =
            TestCreationReducer()

        @Provides
        fun provideViewModel(
            reducer: IReducer<TestCreationModelState, TestCreationState>,
            exceptionHandler: IExceptionHandler,
            getThemes: GetThemes
        ): TestCreationViewModel = TestCreationViewModel(
            reducer,
            getThemes,
            exceptionHandler
        )
    }
}