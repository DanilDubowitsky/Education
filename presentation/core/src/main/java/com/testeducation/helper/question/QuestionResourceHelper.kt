package com.testeducation.helper.question

import com.testeducation.domain.model.question.AnswerItem
import com.testeducation.domain.model.question.QuestionItem
import com.testeducation.helper.resource.ColorResource
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.helper.resource.StringResource
import com.testeducation.utils.getColor
import com.testeducation.utils.getString

interface IQuestionResourceHelper {
    fun getQuestionItemPrepared(questionItems: List<QuestionItem>): List<QuestionItem>
}

class QuestionResourceHelper(
    private val resourceHelper: IResourceHelper,
    private val questionDrawableIconByType: IQuestionDrawableIconByType
) : IQuestionResourceHelper {
    override fun getQuestionItemPrepared(questionItems: List<QuestionItem>): List<QuestionItem> {
        return questionItems.mapIndexed { index, questionItem ->
            questionItem.copy(
                answers = questionItem.answers.map { answerItem ->
                    answerItem.prepareResource()
                },
                numberQuestion = StringResource.Question.NumberQuestion(index + 1)
                    .getString(resourceHelper),
                icon = questionDrawableIconByType.getDrawableIcon(questionItem.type)
            )
        }
    }

    private fun AnswerItem.prepareResource() = when (this) {
        is AnswerItem.DefaultAnswer -> {
            val isTrueColor = if (isTrue) {
                ColorResource.Main.Green.getColor(resourceHelper)
            } else ColorResource.Main.Red.getColor(resourceHelper)
            copy(
                resource = AnswerItem.DefaultAnswer.Resource(
                    isTrueColor = isTrueColor
                )
            )
        }

        is AnswerItem.TextAnswer -> {
            this
        }

        is AnswerItem.MatchAnswer -> {
            this
        }

        is AnswerItem.OrderAnswer -> {
            this
        }

        is AnswerItem.FooterPlusAdd -> {
            this
        }
    }
}