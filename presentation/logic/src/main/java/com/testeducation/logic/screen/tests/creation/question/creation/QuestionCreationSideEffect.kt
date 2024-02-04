package com.testeducation.logic.screen.tests.creation.question.creation

sealed interface QuestionCreationSideEffect {
    object LoaderVisible : QuestionCreationSideEffect
    object LoaderInvisible : QuestionCreationSideEffect
    object ClearFocus: QuestionCreationSideEffect
}