package com.testeducation.education.di.modules.screen.tests.edit

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.test.GetTestDetails
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.question.IQuestionDrawableIconByType
import com.testeducation.helper.question.IQuestionResourceHelper
import com.testeducation.helper.question.QuestionDrawableIconByType
import com.testeducation.helper.question.QuestionResourceHelper
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.logic.screen.tests.edit.TestEditorState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
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
        fun questionDrawableIconByType(resourceHelper: IResourceHelper): IQuestionDrawableIconByType {
            return QuestionDrawableIconByType(
                resourceHelper
            )
        }

        @Provides
        fun provideQuestionResourceHelper(
            resourceHelper: IResourceHelper,
            questionDrawableIconByType: IQuestionDrawableIconByType
        ): IQuestionResourceHelper {
            return QuestionResourceHelper(
                resourceHelper, questionDrawableIconByType
            )
        }

        @Provides
        fun provideReducer(resourceHelper: IResourceHelper,): IReducer<TestEditorModelState, TestEditorState> =
            TestEditorReducer(resourceHelper)

        @Provides
        fun provideViewModel(
            fragment: TestEditorFragment,
            reducer: IReducer<TestEditorModelState, TestEditorState>,
            exceptionHandler: IExceptionHandler,
            resourceHelper: IResourceHelper,
            getTestDetails: GetTestDetails,
            questionResourceHelper: IQuestionResourceHelper,
            navigationRouter: NavigationRouter
        ): TestEditorViewModel {
            val screen = fragment.getScreen<NavigationScreen.Tests.Details>()
            return TestEditorViewModel(
                reducer = reducer,
                exceptionHandler = exceptionHandler,
                resourceHelper = resourceHelper,
                testId = screen.testId,
                getTestDetails = getTestDetails,
                questionResourceHelper = questionResourceHelper,
                router = navigationRouter
            )
        }
    }
}