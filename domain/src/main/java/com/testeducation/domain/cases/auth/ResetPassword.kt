package com.testeducation.domain.cases.auth

class ResetPassword {

    suspend operator fun invoke(
        email: String,
        newPassword: String,
        repeatedPassword: String,
        token: String
    ) {

    }
}