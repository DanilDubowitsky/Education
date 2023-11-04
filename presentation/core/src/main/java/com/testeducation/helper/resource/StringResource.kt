package com.testeducation.helper.resource

sealed interface StringResource {

    sealed interface Common : StringResource {

        object ConfirmExitString : Common

        object CommonConfirmation : Common

        object CommonCancellation : Common

        object CommonBack : Common

        object CommonCancel : Common

        object CommonNext : Common

        object CommonSave : Common
    }

    sealed interface Error : StringResource {

        object EmailIsEmptyString : Error

        object PasswordIsEmptyString : Error

        object TitleCreationTestEmpty : Error

        object TitleCreationTestMaxLine : Error

    }

    sealed interface Update : StringResource {

        object UpdateRequiredError : Update
    }

    sealed interface Question : StringResource {
        data class NumberQuestion(val number: Int) : Question
        data class TimeQuestionMore(val minutes: String, val seconds: String) : Question
        data class TimeQuestionOnlyMinutes(val minutes: String) : Question
        data class TimeQuestionLess(val seconds: String) : Question
        data class MaxLengthAnswer(val count: Int) : Question
    }

    sealed interface StringSettings : StringResource {
        object TestTitle : StringSettings
        object DesignTitle : StringSettings
        object ThemeTitle : StringSettings
        object TestAvailabilityTitle : StringSettings
        object QuestionOrderTitle : StringSettings
        object MinCorrectAnswerTitle : StringSettings
        object PreShowQuestionTitle : StringSettings
        object AntiCheatTitle : StringSettings
        object AntiCheatDescription : StringSettings
        object AvailabilityValueAll : StringSettings
        object AvailabilityValueLink : StringSettings
        object OrderValueOrder : StringSettings
        object OrderValueRandom : StringSettings
    }

    sealed interface Validate : StringResource {
        object TestEditErrorTitle : Validate
        object QuestionCreationErrorTitle : Validate
        data class MaxQuestionValue(val count: Int) : Validate
        object EmptyQuestionCreation : Validate
        object OneAnswerQuestionCreation : Validate
        data class MinCountAnswer(val count: Int) : Validate
        object MinOneTrueAnswer : Validate
        object AnswerIsEmpty: Validate
    }
}