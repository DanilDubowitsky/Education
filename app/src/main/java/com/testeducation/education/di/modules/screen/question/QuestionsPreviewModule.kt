package com.testeducation.education.di.modules.screen.question

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.question.GetQuestions
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.question.ITimeConverterLongToString
import com.testeducation.helper.question.TimeConverterLongToString
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.logic.screen.questions.QuestionsPreviewState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.screen.questions.QuestionsPreviewModelState
import com.testeducation.screen.questions.QuestionsPreviewReducer
import com.testeducation.screen.questions.QuestionsPreviewViewModel
import com.testeducation.ui.screen.questions.QuestionsPreviewDialog
import com.testeducation.ui.utils.getScreen
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface QuestionsPreviewModule {

    @Binds
    @IntoMap
    @ViewModelKey(QuestionsPreviewViewModel::class)
    fun bindViewModel(viewModel: QuestionsPreviewViewModel): ViewModel

    companion object {
        @Provides
        fun questionTimeConverter(resourceHelper: IResourceHelper): ITimeConverterLongToString =
            TimeConverterLongToString(resourceHelper)

        @Provides
        fun provideReducer(
            timeConverter: ITimeConverterLongToString
        ): IReducer<QuestionsPreviewModelState, QuestionsPreviewState> =
            QuestionsPreviewReducer(timeConverter)

        @Provides
        fun provideViewModel(
            dialog: QuestionsPreviewDialog,
            router: NavigationRouter,
            reducer: IReducer<QuestionsPreviewModelState, QuestionsPreviewState>,
            exceptionHandler: IExceptionHandler,
            getQuestions: GetQuestions
        ): QuestionsPreviewViewModel {
            val screen = dialog.getScreen<NavigationScreen.Questions.QuestionsPreview>()
            return QuestionsPreviewViewModel(
                reducer,
                exceptionHandler,
                screen.testId,
                getQuestions
            )
        }
    }

}
