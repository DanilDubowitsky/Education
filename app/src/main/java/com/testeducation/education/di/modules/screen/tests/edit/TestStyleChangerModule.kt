package com.testeducation.education.di.modules.screen.tests.edit

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.test.UpdateTestStyle
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.logic.screen.tests.settings.TestStyleChangerState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.screen.tests.edit.style.TestStyleChangerModelState
import com.testeducation.screen.tests.edit.style.TestStyleChangerReducer
import com.testeducation.screen.tests.edit.style.TestStyleChangerViewModel
import com.testeducation.ui.screen.tests.edit.TestStyleChangerFragment
import com.testeducation.ui.utils.getScreen
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface TestStyleChangerModule {
    @Binds
    @IntoMap
    @ViewModelKey(TestStyleChangerViewModel::class)
    fun bindViewModel(viewModel: TestStyleChangerViewModel): ViewModel

    companion object {
        @Provides
        fun provideReducer(): IReducer<TestStyleChangerModelState, TestStyleChangerState> =
            TestStyleChangerReducer()

        @Provides
        fun provideViewModel(
            fragment: TestStyleChangerFragment,
            reducer: IReducer<TestStyleChangerModelState, TestStyleChangerState>,
            exceptionHandler: IExceptionHandler,
            resourceHelper: IResourceHelper,
            updateTestStyle: UpdateTestStyle,
            router: NavigationRouter,
        ): TestStyleChangerViewModel {
            val screen = fragment.getScreen<NavigationScreen.Tests.TestStyleChangerData>()
            return TestStyleChangerViewModel(
                reducer = reducer,
                exceptionHandler = exceptionHandler,
                resourceHelper = resourceHelper,
                updateTestStyle = updateTestStyle,
                testId = screen.testId,
                color = screen.color,
                backgroundImage = screen.background,
                themeName = screen.themeName,
                router = router
            )
        }
    }
}