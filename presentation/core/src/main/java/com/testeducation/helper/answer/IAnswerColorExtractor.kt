package com.testeducation.helper.answer

interface IAnswerColorExtractor {
    fun extractAnswersColors(
        withAlpha: Boolean = false
    ): List<Int>
}
