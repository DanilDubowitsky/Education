package com.testeducation.education.di.modules.fragment.home.tests.creation

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.theme.GetThemes
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.tests.creation.TestCreationState
import com.testeducation.screen.tests.creation.TestCreationModelState
import com.testeducation.screen.tests.creation.TestCreationReducer
import com.testeducation.screen.tests.creation.TestCreationViewModel
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