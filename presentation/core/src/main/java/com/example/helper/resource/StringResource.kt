package com.example.helper.resource

sealed interface StringResource {

    sealed interface Common : StringResource {

        object ConfirmExitString : Common

        object CommonConfirmation : Common

        object CommonCancellation : Common
    }
}