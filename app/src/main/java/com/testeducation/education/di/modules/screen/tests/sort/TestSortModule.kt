package com.testeducation.education.di.modules.screen.tests.sort

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.local.converter.toEnumModel
import com.testeducation.logic.screen.tests.sort.TestSortState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.screen.tests.sort.TestSortModelState
import com.testeducation.screen.tests.sort.TestSortReducer
import com.testeducation.screen.tests.sort.TestSortViewModel
import com.testeducation.ui.screen.tests.sort.TestSortDialog
import com.testeducation.ui.utils.getScreen
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface TestSortModule {
    @Binds
    @IntoMap
    @ViewModelKey(TestSortViewModel::class)
    fun bindViewModel(viewModel: TestSortViewModel): ViewModel

    companion object {
        @Provides
        fun provideReducer(resourceHelper: IResourceHelper): IReducer<TestSortModelState,
                TestSortState> =
            TestSortReducer(resourceHelper)

        @Provides
        fun provideViewModel(
            dialog: TestSortDialog,
            reducer: IReducer<TestSortModelState, TestSortState>,
            exceptionHandler: IExceptionHandler,
            router: NavigationRouter
        ): TestSortViewModel {
            val screen = dialog.getScreen<NavigationScreen.Tests.TestSort>()
            return TestSortViewModel(
                reducer = reducer,
                exceptionHandler = exceptionHandler,
                selectedSort = screen.orderField.toEnumModel(),
                sortDirection = screen.direction.toEnumModel(),
                router = router
            )
        }
    }
}
