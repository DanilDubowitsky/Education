package com.testeducation.ui.helper.extractor

import com.testeducation.helper.resource.StringResource
import com.testeducation.ui.R

fun StringResource.Profile.extractResourceId() = when (this) {
    StringResource.Profile.DeleteConfirmTitle -> R.string.profile_detele_confirm_title
    StringResource.Profile.DeleteConfirmDescription -> R.string.profile_detele_confirm_description
    StringResource.Profile.DeleteCodeConfirm -> R.string.profile_detele_confirm_delete_description
    StringResource.Profile.LogoutTitle -> R.string.profile_logout_title
    StringResource.Profile.LogoutDescription -> R.string.profile_logout_description
    StringResource.Profile.LogoutSubmit -> R.string.profile_logout_button_submit
    StringResource.Profile.LogoutCancel -> R.string.profile_logout_button_cancel
}