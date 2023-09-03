package com.testeducation.domain.model.question

enum class QuestionType(val typeName: String) {
    MATCH("Match"), DEFAULT("Choice"), WRITE_ANSWER("Text")
}