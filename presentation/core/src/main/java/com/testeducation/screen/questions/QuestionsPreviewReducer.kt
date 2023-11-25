package com.testeducation.screen.questions

import com.testeducation.converter.test.question.toPreviewUIs
import com.testeducation.core.IReducer
import com.testeducation.helper.answer.IAnswerColorExtractor
import com.testeducation.helper.question.ITimeConverterLongToString
import com.testeducation.logic.screen.questions.QuestionsPreviewState

class QuestionsPreviewReducer(
    private val timeConverterLongToString: ITimeConverterLongToString,
    private val answerColorExtractor: IAnswerColorExtractor
) : IReducer<QuestionsPreviewModelState, QuestionsPreviewState> {

    override fun reduce(modelState: QuestionsPreviewModelState): QuestionsPreviewState {

        return QuestionsPreviewState(
            questions = modelState.questions.toPreviewUIs(
                answerColorExtractor,
                timeConverterLongToString
            ),
            isLoading = modelState.loadingState == QuestionsPreviewModelState.LoadingState.LOADING
        )
    }
}
