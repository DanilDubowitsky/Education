package com.testeducation.helper.question

import com.testeducation.domain.model.question.QuestionType
import com.testeducation.helper.resource.DrawableResource
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.utils.getDrawable

interface IQuestionDrawableIconByType {
    fun getDrawableIcon(type: QuestionType): Int
}

class QuestionDrawableIconByType(private val resourceHelper: IResourceHelper) :
    IQuestionDrawableIconByType {
    override fun getDrawableIcon(type: QuestionType): Int = when (type) {
        QuestionType.MATCH -> {
            DrawableResource.MatchIconQuestion.getDrawable(resourceHelper)
        }

        QuestionType.CHOICE -> {
            DrawableResource.DefaultIconQuestion.getDrawable(resourceHelper)
        }

        QuestionType.TEXT -> {
            DrawableResource.WriteAnswerIconQuestion.getDrawable(resourceHelper)
        }

        QuestionType.REORDER -> {
            DrawableResource.OrderAnswerIconQuestion.getDrawable(resourceHelper)
        }
    }
}