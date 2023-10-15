package com.testeducation.domain.cases.test

import com.testeducation.domain.model.test.TestSettingsItem
import com.testeducation.domain.repository.test.ITestRepository

class UpdateTestSettings(
    private val testsRepository: ITestRepository
) {
    suspend operator fun invoke(id: String, testSettings: TestSettingsItem) =
        testsRepository.updateTestSettings(id, testSettings)
}
