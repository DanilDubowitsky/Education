package com.testeducation.screen.tests.pass

import com.testeducation.converter.test.question.toUI
import com.testeducation.core.IReducer
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
        val questionUI = currentState.question?.toPassingQuestion()
            ?.toUI(answerColorExtractor, timeConverterLongToString)

        val matchDataUI = when {
            currentState is TestPassingModelState.SelectedQuestionState.Match &&
                    questionUI is QuestionUI.Match -> {
                questionUI.answers.mapIndexed { index, matchAnswer ->
                    TestPassingState.MatchDataUI(
                        currentState.matchData[index].text,
                        matchAnswer.color
                    )
                }
            }

            questionUI is QuestionUI.Order -> {
                questionUI.answers.mapIndexed { index, matchAnswer ->
                    TestPassingState.MatchDataUI(index.toString(), matchAnswer.color)
                }
            }

            else -> emptyList()
        }

        return TestPassingState(
            currentQuestion = questionUI,
            matchData = matchDataUI
        )
    }
}
