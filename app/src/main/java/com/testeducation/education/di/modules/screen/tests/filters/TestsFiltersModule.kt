package com.testeducation.education.di.modules.screen.tests.filters

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.theme.GetThemes
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.tests.filters.TestsFiltersState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.screen.tests.filters.TestsFiltersModelState
import com.testeducation.screen.tests.filters.TestsFiltersReducer
import com.testeducation.screen.tests.filters.TestsFiltersViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface TestsFiltersModule {

    @Binds
    @IntoMap
    @ViewModelKey(TestsFiltersViewModel::class)
    fun bindViewModel(viewModel: TestsFiltersViewModel): ViewModel

    companion object {
        @Provides
        fun provideReducer(): IReducer<TestsFiltersModelState, TestsFiltersState> =
            TestsFiltersReducer()

        @Provides
        fun provideViewModel(
            getThemes: GetThemes,
            router: NavigationRouter,
            reducer: IReducer<TestsFiltersModelState, TestsFiltersState>,
            exceptionHandler: IExceptionHandler
        ): TestsFiltersViewModel = TestsFiltersViewModel(
            router,
            getThemes,
            reducer,
            exceptionHandler
        )
    }

}
