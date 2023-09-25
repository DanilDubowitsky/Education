package com.testeducation.domain.model.question

import java.awt.Choice

enum class QuestionType(val typeName: String) {
    MATCH("Match"), DEFAULT("Choice"), WRITE_ANSWER("Text");

    companion object {
        fun getType(type: String) = when (type) {
            "Match" -> MATCH
            "Text" -> WRITE_ANSWER
            else -> DEFAULT
        }

    }
}