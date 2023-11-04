package com.testeducation.education.di.modules.screen.tests.question

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.question.GetQuestionDetails
import com.testeducation.domain.cases.question.QuestionCreate
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.question.ITimeConverterLongToString
import com.testeducation.helper.question.TimeConverterLongToString
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.logic.screen.tests.creation.question.creation.QuestionCreationState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.screen.tests.creation.question.creation.QuestionCreationModelState
import com.testeducation.screen.tests.creation.question.creation.QuestionCreationReducer
import com.testeducation.screen.tests.creation.question.creation.QuestionCreationViewModel
import com.testeducation.ui.screen.tests.creation.QuestionCreationFragment
import com.testeducation.ui.utils.getScreen
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface QuestionCreationModule {

    @Binds
    @IntoMap
    @ViewModelKey(QuestionCreationViewModel::class)
    fun bindViewModel(viewModel: QuestionCreationViewModel): ViewModel

    companion object {
        @Provides
        fun questionTimeConverter(resourceHelper: IResourceHelper): ITimeConverterLongToString {
            return TimeConverterLongToString(resourceHelper)
        }

        @Provides
        fun provideReducerQuestionCreation(
            timeConverterLongToString: ITimeConverterLongToString
        ): IReducer<QuestionCreationModelState, QuestionCreationState> =
            QuestionCreationReducer(timeConverterLongToString)

        @Provides
        fun provideViewModelQuestionCreation(
            fragment: QuestionCreationFragment,
            reducer: IReducer<QuestionCreationModelState, QuestionCreationState>,
            exceptionHandler: IExceptionHandler,
            resourceHelper: IResourceHelper,
            router: NavigationRouter,
            questionCreate: QuestionCreate,
            getQuestionDetails: GetQuestionDetails
        ): QuestionCreationViewModel {
            val screen = fragment.getScreen<NavigationScreen.Questions.QuestionEditor>()
            return QuestionCreationViewModel(
                reducer = reducer,
                errorHandler = exceptionHandler,
                resourceHelper = resourceHelper,
                questionTypeItem = screen.questionTypeUiItem,
                router = router,
                questionCreate = questionCreate,
                testId = screen.testId,
                orderQuestion = screen.orderQuestion,
                questionId = screen.questionId,
                getQuestionDetails = getQuestionDetails
            )
        }
    }

}