package com.testeducation.screen.tests.pass

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.question.GetQuestions
import com.testeducation.domain.cases.test.GetTest
import com.testeducation.domain.cases.test.PassTest
import com.testeducation.domain.model.answer.Answer
import com.testeducation.domain.model.question.PassingQuestion
import com.testeducation.domain.model.question.Question
import com.testeducation.domain.model.question.TestPassResult
import com.testeducation.domain.model.question.input.InputUserAnswerData
import com.testeducation.domain.utils.SECOND_IN_MILLIS
import com.testeducation.helper.answer.toPassingQuestions
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.tests.pass.TestPassingSideEffect
import com.testeducation.logic.screen.tests.pass.TestPassingState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import java.util.Collections


private typealias Syntax = SimpleSyntax<TestPassingState, TestPassingSideEffect>

class TestPassingViewModel(
    reducer: IReducer<TestPassingModelState, TestPassingState>,
    exceptionHandler: IExceptionHandler,
    private val router: NavigationRouter,
    private val testId: String,
    private val getTest: GetTest,
    private val passTest: PassTest,
    private val getQuestions: GetQuestions
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

    fun submitAnswer(questionRemainingTime: Long, testRemainingTime: Long) = intent {
        val currentQuestion = getModelState().currentQuestion ?: return@intent
        val isAnswered =
            currentQuestion.state != PassingQuestion.AnswerState.NONE
        if (!isAnswered) {
            when (currentQuestion.question) {
                is Question.Choice -> checkChoiceAnswer(questionRemainingTime)
                is Question.Match -> checkMatchAnswers(questionRemainingTime)
                is Question.Order -> checkOrderAnswers(questionRemainingTime)
                is Question.Text -> applyTextAnswer(questionRemainingTime)
            }
        } else {
            moveToNextQuestion(testRemainingTime)
        }
    }

    fun swapAnswers(fromPosition: Int, toPosition: Int) = intent {
        withContext(Dispatchers.Main) {
            if (fromPosition == toPosition) return@withContext
            val modelState = getModelState()

            when (val state = modelState.selectedQuestionState) {
                is TestPassingModelState.SelectedQuestionState.Match -> {
                    val answers = state.question?.answers?.toMutableList() ?: return@withContext
                    Collections.swap(answers, fromPosition, toPosition)
                    val newQuestion = state.question.copy(answers = answers)
                    val newState = state.copy(question = newQuestion)
                    updateModelState {
                        copy(selectedQuestionState = newState)
                    }
                }

                is TestPassingModelState.SelectedQuestionState.Order -> {
                    val answers = state.question?.answers?.toMutableList() ?: return@withContext
                    Collections.swap(answers, fromPosition, toPosition)
                    val newQuestion = state.question.copy(answers = answers)
                    val newState = state.copy(question = newQuestion)
                    updateModelState {
                        copy(selectedQuestionState = newState)
                    }
                }

                is TestPassingModelState.SelectedQuestionState.Choice,
                is TestPassingModelState.SelectedQuestionState.Text -> return@withContext
            }
        }
    }

    fun onAnswerTextChanged(text: String) = intent {
        val state = getModelState().selectedQuestionState.toText()
        if (text == state.answeredText) return@intent
        updateModelState {
            copy(selectedQuestionState = state.copy(answeredText = text))
        }
    }

    fun completeTest(testRemainingTime: Long, isCheating: Boolean = false) = intent {
        val modelState = getModelState()
        val test = modelState.test ?: return@intent
        val answers = modelState.questions.toInputAnswers()
        val timeInMillis = test.settings.timeLimit * SECOND_IN_MILLIS
        val spentTime = timeInMillis - testRemainingTime

        val correctAnswersCount = answers.count {
            it.isCorrect != null && it.isCorrect!!
        }
        val incorrectAnswersCount = answers.count {
            it.isCorrect != null && !it.isCorrect!!
        }

        val isPassed = correctAnswersCount >= test.settings.minCorrectAnswers
        val result = if (isPassed) {
            TestPassResult.SUCCESSFUL
        } else {
            TestPassResult.FAILED
        }
        val resultScreen = NavigationScreen.Tests.Result(
            correctAnswersCount,
            incorrectAnswersCount,
            isPassed
        )

        router.navigateTo(resultScreen, addToBackStack = false)
        router.setResultListener(NavigationScreen.Tests.Result.OpenResults) {
            router.exit()
            router.navigateTo(NavigationScreen.Tests.Statistic(testId))
        }
        router.setResultListener(NavigationScreen.Tests.Result.OpenMainPage) {
            router.exit()
        }
        passTest(testId, answers, spentTime, isCheating, result)
    }

    fun increaseResumeCount(testRemainingTime: Long) = intent {
        val modelState = getModelState()
        val test = modelState.test ?: return@intent
        if (!test.settings.antiCheating) return@intent
        val newResumeCount = modelState.resumeCount + 1
        if (newResumeCount >= CHEAT_RESUME_COUNT) {
            completeTest(testRemainingTime, isCheating = true)
        }
        updateModelState {
            copy(resumeCount = newResumeCount)
        }
    }

    private fun applyTextAnswer(questionRemainingTime: Long) = intent {
        val modelState = getModelState()
        val currentQuestion = modelState.currentQuestion ?: return@intent
        val selectedQuestionState = modelState.selectedQuestionState.toText()
        val spentTime = modelState.currentQuestion.question.time - questionRemainingTime
        val questions = modelState.questions.toMutableList()

        val newQuestion = currentQuestion.copy(
            timeSpent = spentTime,
            customAnswer = selectedQuestionState.answeredText
        )

        questions[modelState.currentQuestionIndex] = newQuestion

        updateModelState {
            copy(
                currentQuestion = newQuestion,
                questions = questions
            )
        }
    }

    private fun checkMatchAnswers(questionRemainingTime: Long) = intent {
        val modelState = getModelState()
        val state = modelState.selectedQuestionState.toMatch()
        var isCorrect = true
        state.question?.answers?.forEachIndexed { index, matchAnswer ->
            if (state.matchData[index] != matchAnswer.matchedCorrectText) {
                isCorrect = false
                return@forEachIndexed
            }
        }
        val answerState = if (isCorrect) {
            PassingQuestion.AnswerState.CORRECT
        } else {
            PassingQuestion.AnswerState.INCORRECT
        }
        val spentTime = modelState.currentQuestion!!.question.time - questionRemainingTime
        val newQuestion = modelState.currentQuestion.copy(
            state = answerState,
            answers = state.question!!.answers.map(Answer::id),
            timeSpent = spentTime
        )
        val questions = modelState.questions.toMutableList()
        questions[modelState.currentQuestionIndex] = newQuestion
        updateModelState {
            copy(currentQuestion = newQuestion, questions = questions)
        }
    }

    private fun checkOrderAnswers(questionRemainingTime: Long) = intent {
        val modelState = getModelState()
        val questionState = modelState.selectedQuestionState.toOrder()
        val answers = questionState.question?.answers ?: return@intent
        var isCorrect = true
        answers.forEachIndexed { index, orderAnswer ->
            if (index + 1 != orderAnswer.order) {
                isCorrect = false
                return@forEachIndexed
            }
        }
        val state = if (isCorrect) {
            PassingQuestion.AnswerState.CORRECT
        } else {
            PassingQuestion.AnswerState.INCORRECT
        }
        val spentTime = modelState.currentQuestion!!.question.time - questionRemainingTime

        val questions = modelState.questions.toMutableList()

        val newQuestion = modelState.currentQuestion.copy(
            state = state,
            answers = answers.map(Answer::id),
            timeSpent = spentTime
        )

        questions[modelState.currentQuestionIndex] = newQuestion

        updateModelState {
            copy(
                currentQuestion = newQuestion,
                questions = questions
            )
        }
    }

    private fun moveToNextQuestion(testRemainingTime: Long) = intent {
        val modelState = getModelState()
        val questionIndex = modelState.currentQuestionIndex + 1

        if (questionIndex >= modelState.questions.size) {
            completeTest(testRemainingTime)
            return@intent
        }

        val currentQuestion = modelState.questions[questionIndex]
        updateModelState {
            copy(
                currentQuestionIndex = questionIndex,
                currentQuestion = currentQuestion
            )
        }
        val selectedQuestionState = extractQuestionState()
        updateModelState {
            copy(
                selectedQuestionState = selectedQuestionState
            )
        }

        postSideEffect(
            TestPassingSideEffect.StartQuestionTimer(
                currentQuestion.question.time
            )
        )
    }

    private suspend fun Syntax.checkChoiceAnswer(remainingTime: Long) {
        val modelState = getModelState()
        val questionState = modelState.selectedQuestionState.toChoice()
        val index = questionState.selectedAnswerIndex ?: 0
        val selectedAnswer = questionState.question?.answers?.get(index) ?: return
        val state = if (selectedAnswer.isTrue) {
            PassingQuestion.AnswerState.CORRECT
        } else {
            PassingQuestion.AnswerState.INCORRECT
        }
        val spentTime = modelState.currentQuestion!!.question.time - remainingTime

        val questions = modelState.questions.toMutableList()

        val newQuestion = modelState.currentQuestion.copy(
            state = state,
            answers = listOf(selectedAnswer.id),
            timeSpent = spentTime
        )
        questions[modelState.currentQuestionIndex] = newQuestion

        updateModelState {
            copy(
                questions = questions,
                currentQuestion = newQuestion
            )
        }
    }

    private fun loadData() = intent {
        val test = getTest(testId)
        val questions = getQuestions(testId).toPassingQuestions()
        val currentQuestion = questions.first()
        updateModelState {
            copy(
                test = test,
                questions = questions,
                currentQuestion = questions.first()
            )
        }
        val selectedQuestionState = extractQuestionState()
        updateModelState {
            copy(selectedQuestionState = selectedQuestionState)
        }

        val testEffect =
            TestPassingSideEffect.StartTestTimer(test.settings.timeLimit * SECOND_IN_MILLIS)
        postSideEffect(testEffect)
        val questionTime = currentQuestion.question.time
        val questionEffect = TestPassingSideEffect.StartQuestionTimer(questionTime)
        postSideEffect(questionEffect)
    }

    private suspend fun Syntax.extractQuestionState(): TestPassingModelState.SelectedQuestionState {
        val modelState = getModelState()
        val currentQuestion = modelState.currentQuestion!!
        return when (val question = currentQuestion.question) {
            is Question.Match -> TestPassingModelState.SelectedQuestionState.Match(
                question = question,
                matchData = question.answers.extractMatchData()
            )

            is Question.Choice -> TestPassingModelState.SelectedQuestionState.Choice(
                question = question
            )

            is Question.Text -> TestPassingModelState.SelectedQuestionState.Text(
                question = question
            )
            is Question.Order -> TestPassingModelState.SelectedQuestionState.Order(
                question = question
            )
        }
    }

    private fun List<Answer.MatchAnswer>.extractMatchData() =
        map(Answer.MatchAnswer::matchedCorrectText)

    private fun TestPassingModelState.SelectedQuestionState.toChoice(): TestPassingModelState.SelectedQuestionState.Choice =
        this as TestPassingModelState.SelectedQuestionState.Choice

    private fun TestPassingModelState.SelectedQuestionState.toMatch(): TestPassingModelState.SelectedQuestionState.Match =
        this as TestPassingModelState.SelectedQuestionState.Match

    private fun TestPassingModelState.SelectedQuestionState.toText(): TestPassingModelState.SelectedQuestionState.Text =
        this as TestPassingModelState.SelectedQuestionState.Text

    private fun TestPassingModelState.SelectedQuestionState.toOrder(): TestPassingModelState.SelectedQuestionState.Order =
        this as TestPassingModelState.SelectedQuestionState.Order

    private fun PassingQuestion.toInputAnswer(): InputUserAnswerData {
        val isCorrect = if (question is Question.Text) {
            null
        } else {
            state == PassingQuestion.AnswerState.CORRECT
        }
        return InputUserAnswerData(
            question.id,
            answers,
            isCorrect,
            timeSpent,
            customAnswer
        )
    }

    private fun List<PassingQuestion>.toInputAnswers() = map {
        it.toInputAnswer()
    }

    fun exit() = intent {
        router.exit()
    }

    private companion object {
        const val CHEAT_RESUME_COUNT = 1
        fun getMockQuestions() = listOf(
            Question.Choice(
                "12332",
                "Вопрос с ответом 2",
                2,
                60 * SECOND_IN_MILLIS,
                answers = listOf(
                    Answer.ChoiceAnswer("1", "12332", "Ответ 1", false),
                    Answer.ChoiceAnswer("2", "12332", "Ответ 2", true),
                    Answer.ChoiceAnswer("3", "12332", "Ответ 3", false),
                    Answer.ChoiceAnswer("4", "12332", "Ответ 4", false),
                )
            ),
            Question.Order(
                "13",
                "Вопрос с реордером",
                4,
                60 * SECOND_IN_MILLIS,
                answers = listOf(
                    Answer.OrderAnswer("1", "13", "Третий", 2),
                    Answer.OrderAnswer("2", "13", "Второй", 1),
                    Answer.OrderAnswer("3", "13", "Первый", 0),
                    Answer.OrderAnswer("4", "13", "Четвертый", 3),
                )
            ),
            Question.Match(
                "14",
                "Вопрос с матчем",
                5,
                60 * SECOND_IN_MILLIS,
                answers = listOf(
                    Answer.MatchAnswer("1", "14", "Ответ 1", "Текст для матча с 1"),
                    Answer.MatchAnswer("2", "14", "Ответ 2", "Текст для матча с 4"),
                    Answer.MatchAnswer("3", "14", "Ответ 3", "Текст для матча с 3"),
                    Answer.MatchAnswer("4", "14", "Ответ 4", "Текст для матча с 2"),
                )
            ),
            Question.Text(
                "12",
                "Вопрос со свободным ответом",
                3,
                60 * SECOND_IN_MILLIS
            ),
        )
    }

}