package com.testeducation.education.di.modules.screen.tests.code.enter

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.test.GetTestByCode
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.logic.screen.tests.code.enter.TestCodeEnterState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.screen.tests.code.enter.TestCodeEnterModelState
import com.testeducation.screen.tests.code.enter.TestCodeEnterReducer
import com.testeducation.screen.tests.code.enter.TestCodeEnterViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface TestCodeEnterModule {

    @Binds
    @IntoMap
    @ViewModelKey(TestCodeEnterViewModel::class)
    fun bindViewModel(viewModel: TestCodeEnterViewModel): ViewModel

    companion object {

        @Provides
        fun provideReducer(): IReducer<TestCodeEnterModelState, TestCodeEnterState> =
            TestCodeEnterReducer()

        @Provides
        fun provideViewModel(
            reducer: IReducer<TestCodeEnterModelState, TestCodeEnterState>,
            exceptionHandler: IExceptionHandler,
            router: NavigationRouter,
            resourceHelper: IResourceHelper
        ): TestCodeEnterViewModel {

            return TestCodeEnterViewModel(
                reducer,
                exceptionHandler,
                router,
                resourceHelper
            )
        }
    }
}
