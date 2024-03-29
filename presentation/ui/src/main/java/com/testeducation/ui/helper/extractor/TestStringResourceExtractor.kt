package com.testeducation.ui.helper.extractor

import android.content.Context
import com.testeducation.helper.resource.StringResource
import com.testeducation.ui.R

fun StringResource.Test.getStringId(context: Context) = when (this) {
    is StringResource.Test.PublishTitle -> context.getString(R.string.test_publish)
    is StringResource.Test.TestEditExitTitle -> context.getString(R.string.test_exit_edit_title)
    is StringResource.Test.TestQuestionDeleteTitle -> context.getString(R.string.test_question_delete_title)
    is StringResource.Test.TestQuestionDeleteDescription -> context.getString(R.string.test_question_delete_description)
    is StringResource.Test.TestEditExitDescription -> {
        val type = if (this.isDraft) {
            context.getString(R.string.test_exit_edit_draft)
        } else  context.getString(R.string.test_exit_edit_ready)
        context.getString(R.string.test_exit_edit_description, type)
    }
    is StringResource.Test.QuestionCountTitle -> context.resources.getQuantityString(R.plurals.questions_count_plurals, this.count, this.count)
    is StringResource.Test.TestDeleteTitle -> context.getString(R.string.test_delete_title)
    is StringResource.Test.TestDeleteDescription -> context.getString(R.string.test_delete_description)
}