package com.testeducation.helper.test

import com.testeducation.domain.cases.test.ToggleTestLike
import com.testeducation.domain.model.test.TestShort
import com.testeducation.domain.utils.startAsync

class TestHelper(
    private val toggleTestLike: ToggleTestLike,
) : ITestHelper {

    override suspend fun toggleTestLike(
        position: Int,
        tests: List<TestShort>,
        removeFromList: Boolean
    ): List<TestShort> {
        val mutableList = tests.toMutableList()
        val selectedTest = mutableList[position]
        val likesCount = if (selectedTest.liked) selectedTest.likes - 1
        else selectedTest.likes + 1
        val newTest = selectedTest.copy(
            liked = !selectedTest.liked,
            likes = likesCount
        )
        mutableList[position] = newTest
        if (removeFromList) mutableList.remove(newTest)
        startAsync {
            toggleTestLike(selectedTest.id, !selectedTest.liked)
        }
        return mutableList
    }
}
