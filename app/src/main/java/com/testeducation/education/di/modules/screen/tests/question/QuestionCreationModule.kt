package com.testeducation.education.di.modules.screen.tests.question

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.tests.creation.question.creation.QuestionCreationState
import com.testeducation.screen.tests.creation.question.creation.QuestionCreationModelState
import com.testeducation.screen.tests.creation.question.creation.QuestionCreationReducer
import com.testeducation.screen.tests.creation.question.creation.QuestionCreationViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface QuestionCreationModule {

    @Binds
    @IntoMap
    @ViewModelKey(QuestionCreationViewModel::class)
    fun bindViewModelCreationQuestion(viewModel: QuestionCreationViewModel): ViewModel

    companion object {
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