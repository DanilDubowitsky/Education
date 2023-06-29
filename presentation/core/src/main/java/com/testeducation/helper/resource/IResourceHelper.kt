package com.testeducation.helper.resource

import com.testeducation.logic.model.test.CardTestStyle

interface IResourceHelper {
    fun extractStringResource(resource: StringResource): String
    fun extractColorResource(resource: ColorResource): Int

    /**
     * Возвращает изображение на карточке теста из ресурсов
     * */
    fun extractDrawableResource(style: CardTestStyle) : Int
}
