package com.testeducation.helper.question

import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.helper.resource.StringResource

class TimeConverterLongToString(val resourceHelper: IResourceHelper) : ITimeConverterLongToString {
    override fun convert(time: Long): String {
        val minute = time / 60
        val seconds = time.mod(60)

        val stringResource = if (minute == 0L) {
            StringResource.Question.TimeQuestionLess(seconds.toString())
        } else if (seconds == 0) {
            StringResource.Question.TimeQuestionOnlyMinutes(minute.toString())
        } else {
            StringResource.Question.TimeQuestionMore(minute.toString(), seconds.toString())
        }
        return resourceHelper.extractStringResource(stringResource)
    }
}