package com.testeducation.ui.helper.extractor

import com.testeducation.helper.resource.StringResource
import com.testeducation.ui.R

fun StringResource.Profile.extractResourceId() = when (this) {
    StringResource.Profile.DeleteConfirmTitle -> R.string.profile_detele_confirm_title
    StringResource.Profile.DeleteConfirmDescription -> R.string.profile_detele_confirm_description
    StringResource.Profile.DeleteCodeConfirm -> R.string.profile_detele_confirm_delete_description
}