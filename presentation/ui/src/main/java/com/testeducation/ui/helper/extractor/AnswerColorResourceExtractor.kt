package com.testeducation.ui.helper.extractor

import com.testeducation.helper.resource.AnswerColorResource
import com.testeducation.ui.R

fun AnswerColorResource.extractResourceId() = when (this) {
    AnswerColorResource.Blue -> R.color.colorBlue
    AnswerColorResource.Blue60 -> R.color.colorMainBlueLight
    AnswerColorResource.Green -> R.color.colorDarkGreen
    AnswerColorResource.Green60 -> R.color.colorMainGreenLight
    AnswerColorResource.Orange -> R.color.colorOrange
    AnswerColorResource.Orange60 -> R.color.colorMainOrangeLight
    AnswerColorResource.Red -> R.color.colorRed
    AnswerColorResource.Red60 -> R.color.colorMainRedLight
}
