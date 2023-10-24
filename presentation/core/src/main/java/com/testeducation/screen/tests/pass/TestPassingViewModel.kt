package com.testeducation.screen.tests.pass

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.test.GetTest
import com.testeducation.domain.model.question.Answer
import com.testeducation.domain.model.question.Question
import com.testeducation.domain.model.question.QuestionType
import com.testeducation.domain.utils.SECOND_IN_MILLIS
import com.testeducation.helper.answer.toChoiceAnswers
import com.testeducation.helper.answer.toPassingQuestions
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.tests.pass.TestPassingSideEffect
import com.testeducation.logic.screen.tests.pass.TestPassingState
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect

private typealias Syntax = SimpleSyntax<TestPassingState, TestPassingSideEffect>

class TestPassingViewModel(
    reducer: IReducer<TestPassingModelState, TestPassingState>,
    exceptionHandler: IExceptionHandler,
    private val testId: String,
    private val getTest: GetTest
) : BaseViewModel<TestPassingModelState, TestPassingState, TestPassingSideEffect>(
    reducer,
    exceptionHandler
) {

    override val initialModelState: TestPassingModelState = TestPassingModelState()

    init {
        loadData()
    }

    fun selectChoiceAnswer(index: Int) = intent {
        val state = getModelState().selectedQuestionState.toChoice()
        updateModelState {
            copy(selectedQuestionState = state.copy(selectedAnswerIndex = index))
        }
    }

    fun submitAnswer() = intent {
        val currentQuestion = getModelState().currentQuestion ?: return@intent
        when (currentQuestion.question.type) {
            QuestionType.MATCH -> TODO()
            QuestionType.CHOICE -> checkChoiceAnswer()
            QuestionType.TEXT -> TODO()
            QuestionType.REORDER -> TODO()
        }
    }

    fun moveToNextQuestion() = intent {
        updateModelState {
            copy(
                currentQuestionIndex = currentQuestionIndex + 1
            )
        }
        val question = getModelState().currentQuestion
        val selectedQuestionState = extractQuestionState()

        updateModelState {
            copy(selectedQuestionState = selectedQuestionState)
        }

        postSideEffect(
            TestPassingSideEffect.StartQuestionTimer(
                question?.question?.time ?: 0L
            )
        )
    }

    private suspend fun Syntax.checkChoiceAnswer() {
        val modelState = getModelState()
        val questionState = modelState.selectedQuestionState.toChoice()
        val index = questionState.selectedAnswerIndex ?: return
        val selectedAnswer = questionState.answers[index]
        val state = if (selectedAnswer.isTrue) {
            TestPassingModelState.PassingQuestion.AnswerState.CORRECT
        } else {
            TestPassingModelState.PassingQuestion.AnswerState.INCORRECT
        }
        val newQuestion = modelState.currentQuestion?.copy(state = state)
        updateModelState {
            copy(currentQuestion = newQuestion)
        }
    }

    private fun loadData() = intent {
        val test = getTest(testId)
        val questions = getMockQuestions().toPassingQuestions()
        updateModelState {
            copy(questions = questions)
        }
        val effect =
            TestPassingSideEffect.StartTestTimer(test.settings.timeLimit * SECOND_IN_MILLIS)
        postSideEffect(effect)
    }

    private suspend fun Syntax.extractQuestionState(): TestPassingModelState.SelectedQuestionState {
        val modelState = getModelState()
        val question = modelState.currentQuestion!!
        return when (question.question.type) {
            QuestionType.MATCH -> TestPassingModelState.SelectedQuestionState.Match()
            QuestionType.CHOICE -> TestPassingModelState.SelectedQuestionState.Choice(
                answers = question.question.answers.toChoiceAnswers()
            )

            QuestionType.TEXT -> TestPassingModelState.SelectedQuestionState.Text()
            QuestionType.REORDER -> TestPassingModelState.SelectedQuestionState.Order()
        }
    }

    private fun TestPassingModelState.SelectedQuestionState.toChoice(): TestPassingModelState.SelectedQuestionState.Choice =
        this as TestPassingModelState.SelectedQuestionState.Choice

    private fun TestPassingModelState.SelectedQuestionState.toMatch(): TestPassingModelState.SelectedQuestionState.Match =
        this as TestPassingModelState.SelectedQuestionState.Match

    private fun TestPassingModelState.SelectedQuestionState.toText(): TestPassingModelState.SelectedQuestionState.Text =
        this as TestPassingModelState.SelectedQuestionState.Text

    private fun TestPassingModelState.SelectedQuestionState.Order(): TestPassingModelState.SelectedQuestionState.Order =
        this as TestPassingModelState.SelectedQuestionState.Order

    private companion object {
        fun getMockQuestions() = listOf(
            Question(
                "12332",
                "Вопрос с ответом 2",
                "2",
                60 * SECOND_IN_MILLIS,
                QuestionType.CHOICE,
                answers = listOf(
                    Answer.ChoiceAnswer("1", "12332", "Ответ 1", false),
                    Answer.ChoiceAnswer("2", "12332", "Ответ 2", true),
                    Answer.ChoiceAnswer("3", "12332", "Ответ 3", false),
                    Answer.ChoiceAnswer("4", "12332", "Ответ 4", false),
                )
            ),
            Question(
                "12",
                "Вопрос со свободным ответом",
                "3",
                60 * SECOND_IN_MILLIS,
                QuestionType.TEXT,
                answers = listOf(Answer.TextAnswer("1", "12"))
            ),
            Question(
                "13",
                "Вопрос с реордером",
                "4",
                60 * SECOND_IN_MILLIS,
                QuestionType.REORDER,
                answers = listOf(
                    Answer.OrderAnswer("1", "13", "Третий", 3),
                    Answer.OrderAnswer("2", "13", "Второй", 2),
                    Answer.OrderAnswer("3", "13", "Первый", 1),
                    Answer.OrderAnswer("4", "13", "Четвертый", 4),
                )
            ),
            Question(
                "14",
                "Вопрос с матчем",
                "4",
                60 * SECOND_IN_MILLIS,
                QuestionType.MATCH,
                answers = listOf(
                    Answer.MatchAnswer("1", "14", "Текст для матча с 1"),
                    Answer.MatchAnswer("2", "14", "Текст для матча с 4"),
                    Answer.MatchAnswer("3", "14", "Текст для матча с 3"),
                    Answer.MatchAnswer("4", "14", "Текст для матча с 2"),
                )
            ),
        )
    }

}
