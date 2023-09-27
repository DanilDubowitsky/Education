package com.testeducation.helper.question

import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.helper.resource.StringResource

class TimeConverterLongToString(val resourceHelper: IResourceHelper) : ITimeConverterLongToString {
    override fun convert(time: Long): String {
        val minute = time / 60
        val seconds = time.mod(60)

        return if (minute == 0L) {
            StringResource.Question.TimeQuestionLess(seconds.toString())
        } else {
            StringResource.Question.TimeQuestionMore(minute.toString(), seconds.toString())
        }.let {
            resourceHelper.extractStringResource(it)
        }
    }
}