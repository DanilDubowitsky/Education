package com.testeducation.domain.cases.user

import com.testeducation.domain.repository.user.IUserConfirmCodeRepository

class DeleteUser(
    private val userConfirmCodeRepository: IUserConfirmCodeRepository
) {

    suspend operator fun invoke(): String = userConfirmCodeRepository.deleteUser()
}
