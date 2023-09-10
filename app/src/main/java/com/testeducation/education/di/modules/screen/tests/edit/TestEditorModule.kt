package com.testeducation.education.di.modules.screen.tests.edit

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.test.CreateTest
import com.testeducation.domain.cases.theme.GetThemes
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.logic.screen.tests.creation.TestCreationState
import com.testeducation.logic.screen.tests.edit.TestEditorState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.screen.tests.creation.TestCreationModelState
import com.testeducation.screen.tests.creation.TestCreationReducer
import com.testeducation.screen.tests.creation.TestCreationViewModel
import com.testeducation.screen.tests.edit.TestEditorModelState
import com.testeducation.screen.tests.edit.TestEditorReducer
import com.testeducation.screen.tests.edit.TestEditorViewModel
import com.testeducation.ui.screen.tests.edit.TestEditorFragment
import com.testeducation.ui.utils.getScreen
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface TestEditorModule {
    @Binds
    @IntoMap
    @ViewModelKey(TestEditorViewModel::class)
    fun bindViewModel(viewModel: TestEditorViewModel): ViewModel

    companion object {
        @Provides
        fun provideReducer(): IReducer<TestEditorModelState, TestEditorState> =
            TestEditorReducer()

        @Provides
        fun provideViewModel(
            fragment: TestEditorFragment,
            reducer: IReducer<TestEditorModelState, TestEditorState>,
            exceptionHandler: IExceptionHandler,
            resourceHelper: IResourceHelper
        ): TestEditorViewModel {
            val screen = fragment.getScreen<NavigationScreen.Tests.Details>()
            return TestEditorViewModel(
                reducer = reducer,
                exceptionHandler = exceptionHandler,
                resourceHelper = resourceHelper,
                testId = screen.testId
            )
        }
    }
}