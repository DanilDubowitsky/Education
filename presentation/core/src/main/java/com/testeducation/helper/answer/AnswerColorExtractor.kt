package com.testeducation.helper.answer

import com.testeducation.helper.resource.AnswerColorResource
import com.testeducation.helper.resource.IResourceHelper

class AnswerColorExtractor(
    private val resourceHelper: IResourceHelper
) : IAnswerColorExtractor {

    override fun extractAnswersColors(withAlpha: Boolean): List<Int> {
        val greenResource = if (!withAlpha) {
            AnswerColorResource.Green
        } else {
            AnswerColorResource.Green60
        }
        val blueResource = if (!withAlpha) {
            AnswerColorResource.Blue
        } else {
            AnswerColorResource.Blue60
        }
        val orangeResource = if (!withAlpha) {
            AnswerColorResource.Orange
        } else {
            AnswerColorResource.Orange60
        }
        val redResource = if (!withAlpha) {
            AnswerColorResource.Red
        } else {
            AnswerColorResource.Red60
        }

        val blue = resourceHelper.extractColorResource(blueResource)
        val green = resourceHelper.extractColorResource(greenResource)
        val orange = resourceHelper.extractColorResource(orangeResource)
        val red = resourceHelper.extractColorResource(redResource)

        return listOf(blue, green, orange, red)
    }


}
