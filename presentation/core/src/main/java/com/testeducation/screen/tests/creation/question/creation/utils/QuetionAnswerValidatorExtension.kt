package com.testeducation.screen.tests.creation.question.creation.utils

import com.testeducation.domain.model.question.input.InputAnswer
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.helper.resource.StringResource
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.utils.getString

/**
 * Валидация количество правильных ответов и количества неправильных. Не должно быть ситуации, когда нет неправильных ответов
 */
inline fun List<InputAnswer>.validateAnswerTrueAndFalse(
    resource: IResourceHelper,
    error: (NavigationScreen.Common.Information) -> Unit
) {
    val countTrue = count { it is InputAnswer.DefaultAnswer && it.isTrue } != 0
    val countFalse = count { it is InputAnswer.DefaultAnswer && !it.isTrue } == 0
    if (countTrue && countFalse) {
        error(
            NavigationScreen.Common.Information(
                titleText = StringResource.Validate.QuestionCreationErrorTitle.getString(
                    resource
                ),
                description = StringResource.Validate.MinFalseAnswerDescription.getString(resource),
                btnText = StringResource.Common.CommonNext.getString(resource),
            )
        )
    }
}