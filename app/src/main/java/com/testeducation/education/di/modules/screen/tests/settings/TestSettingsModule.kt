package com.testeducation.education.di.modules.screen.tests.settings

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.test.GetTestSettings
import com.testeducation.domain.cases.theme.GetThemes
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.logic.screen.tests.settings.TestSettingsState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.screen.tests.edit.settings.TestSettingsModelState
import com.testeducation.screen.tests.edit.settings.TestSettingsReducer
import com.testeducation.screen.tests.edit.settings.TestSettingsViewModel
import com.testeducation.ui.screen.tests.edit.TestSettingsFragment
import com.testeducation.ui.utils.getScreen
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface TestSettingsModule {
    @Binds
    @IntoMap
    @ViewModelKey(TestSettingsViewModel::class)
    fun bindViewModel(viewModel: TestSettingsViewModel): ViewModel

    companion object {
        @Provides
        fun provideReducer(): IReducer<TestSettingsModelState, TestSettingsState> =
            TestSettingsReducer()

        @Provides
        fun provideViewModel(
            fragment: TestSettingsFragment,
            reducer: IReducer<TestSettingsModelState, TestSettingsState>,
            exceptionHandler: IExceptionHandler,
            resourceHelper: IResourceHelper,
            navigationRouter: NavigationRouter,
            getTestSettings: GetTestSettings,
            getThemes: GetThemes
        ): TestSettingsViewModel {
            val screen = fragment.getScreen<NavigationScreen.Tests.Settings>()
            return TestSettingsViewModel(
                reducer = reducer,
                exceptionHandler = exceptionHandler,
                resourceHelper = resourceHelper,
                testId = screen.testId,
                router = navigationRouter,
                getTestSettings = getTestSettings,
                titleTest = screen.titleTest,
                colorTest = screen.colorTest,
                imageTest = screen.imageTest,
                idTheme = screen.idTheme,
                getThemes = getThemes
            )
        }
    }
}