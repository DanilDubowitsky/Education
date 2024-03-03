package com.testeducation.ui.helper.extractor

import android.content.Context
import com.testeducation.helper.resource.StringResource
import com.testeducation.ui.R

fun StringResource.Test.getStringId(context: Context) = when (this) {
    is StringResource.Test.PublishTitle -> context.getString(R.string.test_publish)
    is StringResource.Test.TestEditExitTitle -> context.getString(R.string.test_exit_edit_title)
    is StringResource.Test.TestEditExitDescription -> {
        val type = if (this.isDraft) {
            context.getString(R.string.test_exit_edit_draft)
        } else  context.getString(R.string.test_exit_edit_ready)
        context.getString(R.string.test_exit_edit_description, type)
    }
}