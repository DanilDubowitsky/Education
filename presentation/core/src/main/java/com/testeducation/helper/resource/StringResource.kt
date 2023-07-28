package com.testeducation.helper.resource

sealed interface StringResource {

    sealed interface Common : StringResource {

        object ConfirmExitString : Common

        object CommonConfirmation : Common

        object CommonCancellation : Common

        object CommonBack: Common

        object CommonCancel: Common

        object CommonNext: Common

        object CommonSave: Common
    }

    sealed interface Error : StringResource {

        object EmailIsEmptyString : Error

        object PasswordIsEmptyString : Error

        object TitleCreationTestEmpty: Error

    }

    sealed interface Update : StringResource {

        object UpdateRequiredError : Update
    }
}