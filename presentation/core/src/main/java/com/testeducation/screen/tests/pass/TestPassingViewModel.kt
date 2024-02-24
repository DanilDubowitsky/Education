package com.testeducation.screen.tests.pass

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.question.GetQuestions
import com.testeducation.domain.cases.test.GetTest
import com.testeducation.domain.cases.test.PassTest
import com.testeducation.domain.cases.user.GetCurrentUser
import com.testeducation.domain.model.answer.Answer
import com.testeducation.domain.model.question.PassingQuestion
import com.testeducation.domain.model.question.Question
import com.testeducation.domain.model.question.TestPassResult
import com.testeducation.domain.model.question.input.InputUserAnswerData
import com.testeducation.domain.model.test.Test
import com.testeducation.domain.model.test.TestSettings
import com.testeducation.domain.utils.SECOND_IN_MILLIS
import com.testeducation.helper.answer.toPassingQuestions
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.tests.pass.TestPassingSideEffect
import com.testeducation.logic.screen.tests.pass.TestPassingState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.utils.firstByCondition
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect

private typealias Syntax = SimpleSyntax<TestPassingState, TestPassingSideEffect>

class TestPassingViewModel(
    reducer: IReducer<TestPassingModelState, TestPassingState>,
    exceptionHandler: IExceptionHandler,
    private val router: NavigationRouter,
    private val testId: String,
    private val getTest: GetTest,
    private val passTest: PassTest,
    private val getQuestions: GetQuestions,
    private val getCurrentUser: GetCurrentUser
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
        val selectedAnswer = state.question?.answers?.get(index) ?: return@intent
        val newList = if (state.selectedIds.contains(selectedAnswer.id)) {
            state.selectedIds.minus(selectedAnswer.id)
        } else {
            state.selectedIds.plus(selectedAnswer.id)
        }
        updateModelState {
            copy(selectedQuestionState = state.copy(selectedIds = newList))
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
                is Question.Text -> {
                    applyTextAnswer(questionRemainingTime)
                }
            }
        } else {
            moveToNextQuestion(testRemainingTime)
        }
    }

    fun swapAnswers(newItemsIds: List<String>) = intent {
        val modelState = getModelState()
        when (val state = modelState.selectedQuestionState) {
            is TestPassingModelState.SelectedQuestionState.Match -> {
                val answers = state.question?.answers ?: return@intent
                val newAnswers = newItemsIds.map { id ->
                    answers.firstByCondition(Answer::id, id)
                }
                val newQuestion = state.question.copy(answers = newAnswers)
                val newState = state.copy(question = newQuestion)
                updateModelState {
                    copy(selectedQuestionState = newState)
                }
            }

                is TestPassingModelState.SelectedQuestionState.Order -> {
                    val answers = state.question?.answers ?: return@intent
                    val newAnswers = newItemsIds.map { id ->
                        answers.firstByCondition(Answer::id, id)
                    }
                    val newQuestion = state.question.copy(answers = newAnswers)
                    val newState = state.copy(question = newQuestion)
                    updateModelState {
                        copy(selectedQuestionState = newState)
                    }
                }

            is TestPassingModelState.SelectedQuestionState.Choice,
            is TestPassingModelState.SelectedQuestionState.Text -> return@intent
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
        postSideEffect(TestPassingSideEffect.EndTimer)
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

        if (isCheating || testRemainingTime < 0L) {
            router.navigateTo(
                NavigationScreen.Tests.FailedResult(isCheating),
                addToBackStack = false
            )
        } else {
            router.navigateTo(resultScreen, addToBackStack = false)
        }
        router.setResultListener(NavigationScreen.Tests.Result.OpenResults) {
            router.exit()
            router.navigateTo(NavigationScreen.Tests.Statistic(testId))
        }
        router.setResultListener(NavigationScreen.Tests.Result.OpenMainPage) {
            router.exit()
        }
        if (test.status != Test.Status.DRAFT && test.creator.id != modelState.currentUser?.id) {
            passTest(testId, answers, spentTime, isCheating, result)
        }
    }

    fun increaseResumeCount(testRemainingTime: Long) = intent {
        val modelState = getModelState()
        val test = modelState.test ?: return@intent
        if (!test.settings.antiCheating) return@intent
        val newResumeCount = modelState.resumeCount + 1
        if (newResumeCount == CHEAT_RESUME_COUNT) {
            completeTest(testRemainingTime, isCheating = true)
        }
        updateModelState {
            copy(resumeCount = newResumeCount)
        }
    }

    fun exit() = intent {
        router.exit()
    }

    fun onAnswerClick(position: Int) = intent {
        val modelState = getModelState()
        val answer = when (val question = modelState.currentQuestion?.question) {
            is Question.Choice -> question.answers[position]
            is Question.Match -> question.answers[position]
            is Question.Order -> question.answers[position]
            is Question.Text,
            null -> return@intent
        }
        val screen = NavigationScreen.Tests.FullAnswer(answer.extractText())
        router.navigateTo(screen)
    }

    private fun applyTextAnswer(questionRemainingTime: Long) = intent {
        val modelState = getModelState()
        val currentQuestion = modelState.currentQuestion ?: return@intent
        val textQuestion = currentQuestion.question as Question.Text
        val selectedQuestionState = modelState.selectedQuestionState.toText()
        val spentTime = modelState.currentQuestion.question.time - questionRemainingTime
        val questions = modelState.questions.toMutableList()

        val answerState =
            if (textQuestion.answers.first().correctText.lowercase() ==
                selectedQuestionState.answeredText.lowercase()
            ) {
                PassingQuestion.AnswerState.CORRECT
            } else {
                PassingQuestion.AnswerState.INCORRECT
            }

        val newQuestion = currentQuestion.copy(
            timeSpent = spentTime,
            customAnswer = selectedQuestionState.answeredText,
            state = answerState
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
                currentQuestion.question.time * SECOND_IN_MILLIS
            )
        )
    }

    private suspend fun Syntax.checkChoiceAnswer(remainingTime: Long) {
        val modelState = getModelState()
        val questionState = modelState.selectedQuestionState.toChoice()
        val selectedAnswers = questionState.question!!.answers.filter { answer ->
            questionState.selectedIds.contains(answer.id)
        }
        val isIncorrect = selectedAnswers.any { answer ->
            !answer.isTrue
        }
        val state = if (!isIncorrect) {
            PassingQuestion.AnswerState.CORRECT
        } else {
            PassingQuestion.AnswerState.INCORRECT
        }
        val spentTime = modelState.currentQuestion!!.question.time - remainingTime
        val questions = modelState.questions.toMutableList()
        val newQuestion = modelState.currentQuestion.copy(
            state = state,
            answers = questionState.selectedIds,
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
        val currentUser = getCurrentUser()
        var questions = getQuestions(testId).toPassingQuestions(isInitial = true)
        if (test.settings.questionsOrder == TestSettings.QuestionsOrder.SHUFFLED) {
            questions = questions.shuffled()
        }
        val currentQuestion = questions.first()
        updateModelState {
            copy(
                test = test,
                questions = questions,
                currentQuestion = questions.first(),
                isLoading = false,
                currentUser = currentUser
            )
        }
        val selectedQuestionState = extractQuestionState()
        updateModelState {
            copy(selectedQuestionState = selectedQuestionState)
        }

        val testEffect =
            TestPassingSideEffect.StartTestTimer(test.settings.timeLimit * SECOND_IN_MILLIS)
        postSideEffect(testEffect)
        val questionTime = currentQuestion.question.time * SECOND_IN_MILLIS
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
        val isCorrect = state == PassingQuestion.AnswerState.CORRECT
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

    private fun Answer.extractText() = when (this) {
        is Answer.ChoiceAnswer -> title
        is Answer.MatchAnswer -> title
        is Answer.OrderAnswer -> title
        is Answer.TextAnswer -> ""
    }

    private companion object {
        const val CHEAT_RESUME_COUNT = 1
    }
}
