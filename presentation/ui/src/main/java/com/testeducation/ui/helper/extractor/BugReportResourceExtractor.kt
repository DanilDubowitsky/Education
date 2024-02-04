package com.testeducation.ui.helper.extractor

import com.testeducation.helper.resource.StringResource
import com.testeducation.ui.R

fun StringResource.BugReport.getText(): Int {
    return when (this) {
        StringResource.BugReport.TitleInfoSuccess -> R.string.support_success_title
        StringResource.BugReport.DescriptionInfoSuccess -> R.string.support_success_description
    }
}