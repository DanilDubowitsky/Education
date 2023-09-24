package com.testeducation.domain.model.question

enum class QuestionType(val typeName: String) {
    MATCH("Match"), DEFAULT("Choice"), WRITE_ANSWER("Text"), ORDER("Reorder");

    companion object {
        fun getType(type: String) = when (type) {
            "Match" -> MATCH
            "Text" -> WRITE_ANSWER
            "Reorder" -> ORDER
            else -> DEFAULT
        }

    }
}