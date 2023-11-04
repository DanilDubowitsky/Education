package com.testeducation.education.di.modules.screen.tests.question.input

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.logic.screen.tests.creation.question.creation.input.AnswerInputState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.screen.tests.creation.question.creation.input.AnswerInputModelState
import com.testeducation.screen.tests.creation.question.creation.input.AnswerInputReducer
import com.testeducation.screen.tests.creation.question.creation.input.AnswerInputViewModel
import com.testeducation.ui.screen.tests.creation.input.AnswerInputDialog
import com.testeducation.ui.utils.getScreen
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface AnswerInputModule {
    @Binds
    @IntoMap
    @ViewModelKey(AnswerInputViewModel::class)
    fun bindViewModel(viewModel: AnswerInputViewModel): ViewModel

    companion object {

        @Provides
        fun provideReducerQuestionCreation(
        ): IReducer<AnswerInputModelState, AnswerInputState> =
            AnswerInputReducer()

        @Provides
        fun provideViewModelQuestionCreation(
            fragment: AnswerInputDialog,
            reducer: IReducer<AnswerInputModelState, AnswerInputState>,
            exceptionHandler: IExceptionHandler,
            resourceHelper: IResourceHelper,
            router: NavigationRouter,
        ): AnswerInputViewModel {
            val screen = fragment.getScreen<NavigationScreen.Questions.AnswerInput>()
            return AnswerInputViewModel(
                reducer = reducer,
                errorHandler = exceptionHandler,
                resourceHelper = resourceHelper,
                router = router,
                answerText = screen.answerText,
                color = screen.color,
                firstAnswer = screen.firstAnswer
            )
        }
    }
}