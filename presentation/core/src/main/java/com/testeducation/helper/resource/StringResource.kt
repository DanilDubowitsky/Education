package com.testeducation.helper.resource

sealed interface StringResource {

    sealed interface Common : StringResource {

        object ConfirmExitString : Common

        object CommonConfirmation : Common

        object CommonCancellation : Common

        object CommonBack: Common

        object CommonCancel: Common
    }

    sealed interface Error : StringResource {

        object EmailIsEmptyString : Error

        object PasswordIsEmptyString : Error

    }

    sealed interface Update : StringResource {

        object UpdateRequiredError : Update
    }
}