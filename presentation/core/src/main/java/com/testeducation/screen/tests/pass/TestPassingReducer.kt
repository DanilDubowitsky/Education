package com.testeducation.screen.tests.pass

import com.testeducation.converter.test.question.toUI
import com.testeducation.core.IReducer
import com.testeducation.domain.model.question.PassingQuestion
import com.testeducation.helper.answer.IAnswerColorExtractor
import com.testeducation.helper.answer.toPassingQuestion
import com.testeducation.helper.question.ITimeConverterLongToString
import com.testeducation.logic.model.question.QuestionUI
import com.testeducation.logic.screen.tests.pass.TestPassingState

class TestPassingReducer(
    private val answerColorExtractor: IAnswerColorExtractor,
    private val timeConverterLongToString: ITimeConverterLongToString
) : IReducer<TestPassingModelState, TestPassingState> {

    override fun reduce(modelState: TestPassingModelState): TestPassingState {
        val currentState = modelState.selectedQuestionState
        val state = modelState.currentQuestion?.state
            ?: PassingQuestion.AnswerState.NONE
        val answers = modelState.currentQuestion?.answers ?: emptyList()
        val timeSpent = modelState.currentQuestion?.timeSpent ?: 0L
        val choiceState = currentState as? TestPassingModelState.SelectedQuestionState.Choice

        val questionUI = currentState.question?.toPassingQuestion(
            spentTime = timeSpent,
            state = state,
            answers = answers
        )?.toUI(
            answerColorExtractor,
            timeConverterLongToString,
            choiceState?.selectedIds ?: emptyList()
        )

        val matchDataUI = when {
            currentState is TestPassingModelState.SelectedQuestionState.Match &&
                    questionUI is QuestionUI.Match -> {
                modelState.currentQuestion!!.matchData.mapIndexed { index, matchAnswer ->
                    TestPassingState.MatchDataUI(
                        matchAnswer,
                        questionUI.answers[index].color
                    )
                }
            }

            questionUI is QuestionUI.Order -> {
                questionUI.answers.mapIndexed { index, matchAnswer ->
                    TestPassingState.MatchDataUI(
                        (index + 1).toString(),
                        matchAnswer.color
                    )
                }
            }

            else -> emptyList()
        }

        return TestPassingState(
            currentQuestion = questionUI,
            matchData = matchDataUI,
            currentQuestionPosition = modelState.currentQuestionIndex + 1,
            questionsCount = modelState.questions.size,
            isAntiCheating = modelState.test?.settings?.antiCheating ?: false,
            isLoading = modelState.isLoading
        )
    }
}
