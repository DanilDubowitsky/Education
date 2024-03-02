package com.testeducation.ui.helper.extractor

import com.testeducation.helper.resource.StringResource
import com.testeducation.ui.R

fun StringResource.Test.getStringId() = when (this) {
    is StringResource.Test.PublishTitle -> R.string.test_publish
}