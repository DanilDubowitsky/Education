package com.testeducation.education.di.modules.screen.tests.question

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.tests.creation.question.SelectionQuestionTypeState
import com.testeducation.logic.screen.tests.creation.question.creation.QuestionCreationState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.screen.tests.creation.question.SelectionQuestionTypeModelState
import com.testeducation.screen.tests.creation.question.SelectionQuestionTypeModelStateReducer
import com.testeducation.screen.tests.creation.question.SelectionQuestionTypeViewModel
import com.testeducation.screen.tests.creation.question.creation.QuestionCreationModelState
import com.testeducation.screen.tests.creation.question.creation.QuestionCreationReducer
import com.testeducation.screen.tests.creation.question.creation.QuestionCreationViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface QuestionModule {
    @Binds
    @IntoMap
    @ViewModelKey(SelectionQuestionTypeViewModel::class)
    fun bindViewModel(viewModel: SelectionQuestionTypeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(QuestionCreationViewModel::class)
    fun bindViewModelCreationQuestion(viewModel: QuestionCreationViewModel): ViewModel

    companion object {
        @Provides
        fun provideReducer(): IReducer<SelectionQuestionTypeModelState, SelectionQuestionTypeState> =
            SelectionQuestionTypeModelStateReducer()

        @Provides
        fun provideViewModel(
            reducer: IReducer<SelectionQuestionTypeModelState, SelectionQuestionTypeState>,
            exceptionHandler: IExceptionHandler,
            navigationRouter: NavigationRouter
        ): SelectionQuestionTypeViewModel = SelectionQuestionTypeViewModel(
            reducer = reducer,
            errorHandler = exceptionHandler,
            router = navigationRouter
        )

        @Provides
        fun provideReducerQuestionCreation(): IReducer<QuestionCreationModelState, QuestionCreationState> =
            QuestionCreationReducer()

        @Provides
        fun provideViewModelQuestionCreation(
            reducer: IReducer<QuestionCreationModelState, QuestionCreationState>,
            exceptionHandler: IExceptionHandler
        ): QuestionCreationViewModel = QuestionCreationViewModel(
            reducer = reducer,
            errorHandler = exceptionHandler
        )
    }

}