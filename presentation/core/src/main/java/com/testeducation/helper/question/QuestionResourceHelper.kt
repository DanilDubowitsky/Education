package com.testeducation.helper.question

import com.testeducation.domain.model.question.input.InputAnswer
import com.testeducation.domain.model.question.input.InputQuestion
import com.testeducation.helper.resource.ColorResource
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.helper.resource.StringResource
import com.testeducation.utils.getColor
import com.testeducation.utils.getString

interface IQuestionResourceHelper {
    fun getQuestionItemPrepared(questions: List<InputQuestion>): List<InputQuestion>
}

class QuestionResourceHelper(
    private val resourceHelper: IResourceHelper,
    private val questionDrawableIconByType: IQuestionDrawableIconByType
) : IQuestionResourceHelper {
    override fun getQuestionItemPrepared(questions: List<InputQuestion>): List<InputQuestion> {
        return questions.mapIndexed { index, questionItem ->
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

    private fun InputAnswer.prepareResource() = when (this) {
        is InputAnswer.DefaultAnswer -> {
            val isTrueColor = if (isTrue) {
                ColorResource.Main.Green.getColor(resourceHelper)
            } else ColorResource.Main.Red.getColor(resourceHelper)
            copy(
                resource = InputAnswer.DefaultAnswer.Resource(
                    isTrueColor = isTrueColor
                )
            )
        }

        is InputAnswer.TextAnswer -> {
            this
        }

        is InputAnswer.MatchAnswer -> {
            this
        }

        is InputAnswer.OrderAnswer -> {
            this
        }

        is InputAnswer.FooterPlusAdd -> {
            this
        }
    }
}
