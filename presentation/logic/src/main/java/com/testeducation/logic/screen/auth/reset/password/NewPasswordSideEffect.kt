package com.testeducation.logic.screen.auth.reset.password

sealed interface NewPasswordSideEffect {
    object PasswordInvalid : NewPasswordSideEffect
    object RepeatedPasswordInvalid : NewPasswordSideEffect
    object PasswordsNotMatch : NewPasswordSideEffect
    object PasswordResetSuccess : NewPasswordSideEffect
}
