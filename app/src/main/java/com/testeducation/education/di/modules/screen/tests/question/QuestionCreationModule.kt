package com.testeducation.education.di.modules.screen.tests.question

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.logic.screen.tests.creation.question.creation.QuestionCreationState
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
        fun provideReducerQuestionCreation(): IReducer<QuestionCreationModelState, QuestionCreationState> =
            QuestionCreationReducer()

        @Provides
        fun provideViewModelQuestionCreation(
            fragment: QuestionCreationFragment,
            reducer: IReducer<QuestionCreationModelState, QuestionCreationState>,
            exceptionHandler: IExceptionHandler,
            resourceHelper: IResourceHelper
        ): QuestionCreationViewModel {
            val screen = fragment.getScreen<NavigationScreen.QuestionCreation.QuestionEditor>()
            return QuestionCreationViewModel(
                reducer = reducer,
                errorHandler = exceptionHandler,
                resourceHelper = resourceHelper,
                questionTypeItem = screen.questionTypeUiItem
            )
        }
    }

}