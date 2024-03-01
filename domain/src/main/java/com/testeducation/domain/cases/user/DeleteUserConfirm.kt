package com.testeducation.domain.cases.user

import com.testeducation.domain.repository.user.IUserConfirmCodeRepository

class DeleteUserConfirm(
    private val userConfirmCodeRepository: IUserConfirmCodeRepository
) {
    suspend operator fun invoke(token: String, code: String) =
        userConfirmCodeRepository.confirmDeleteUser(
            token = token, code = code
        )
}
