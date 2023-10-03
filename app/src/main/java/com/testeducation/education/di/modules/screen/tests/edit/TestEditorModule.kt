package com.testeducation.education.di.modules.screen.tests.edit

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.test.GetTest
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.question.IQuestionDrawableIconByType
import com.testeducation.helper.question.IQuestionResourceHelper
import com.testeducation.helper.question.ITimeConverterLongToString
import com.testeducation.helper.question.QuestionDrawableIconByType
import com.testeducation.helper.question.QuestionResourceHelper
import com.testeducation.helper.question.TimeConverterLongToString
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
        fun questionTimeConverter(resourceHelper: IResourceHelper): ITimeConverterLongToString {
            return TimeConverterLongToString(resourceHelper)
        }
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
        fun provideReducer(timeConverterLongToString: ITimeConverterLongToString): IReducer<TestEditorModelState, TestEditorState> =
            TestEditorReducer(timeConverterLongToString)

        @Provides
        fun provideViewModel(
            fragment: TestEditorFragment,
            reducer: IReducer<TestEditorModelState, TestEditorState>,
            exceptionHandler: IExceptionHandler,
            resourceHelper: IResourceHelper,
            getTest: GetTest,
            questionResourceHelper: IQuestionResourceHelper,
            navigationRouter: NavigationRouter
        ): TestEditorViewModel {
            val screen = fragment.getScreen<NavigationScreen.Tests.Details>()
            return TestEditorViewModel(
                reducer = reducer,
                exceptionHandler = exceptionHandler,
                resourceHelper = resourceHelper,
                testId = screen.testId,
                getTest = getTest,
                questionResourceHelper = questionResourceHelper,
                router = navigationRouter
            )
        }
    }
}