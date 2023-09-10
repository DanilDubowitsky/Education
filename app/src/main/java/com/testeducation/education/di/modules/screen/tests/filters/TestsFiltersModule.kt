package com.testeducation.education.di.modules.screen.tests.filters

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.test.GetLikedTests
import com.testeducation.domain.cases.test.GetTests
import com.testeducation.domain.cases.theme.GetThemes
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.tests.filters.TestsFiltersState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.screen.tests.filters.TestsFiltersModelState
import com.testeducation.screen.tests.filters.TestsFiltersReducer
import com.testeducation.screen.tests.filters.TestsFiltersViewModel
import com.testeducation.ui.screen.tests.filters.TestsFiltersFragment
import com.testeducation.ui.utils.getScreen
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
            fragment: TestsFiltersFragment,
            getThemes: GetThemes,
            router: NavigationRouter,
            getTests: GetTests,
            getLikedTests: GetLikedTests,
            reducer: IReducer<TestsFiltersModelState, TestsFiltersState>,
            exceptionHandler: IExceptionHandler
        ): TestsFiltersViewModel {
            val screen = fragment.getScreen<NavigationScreen.Tests.Filters>()
            return TestsFiltersViewModel(
                screen.filters,
                router,
                getThemes,
                getTests,
                getLikedTests,
                reducer,
                exceptionHandler
            )
        }
    }

}
