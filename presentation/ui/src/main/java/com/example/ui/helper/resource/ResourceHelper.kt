package com.example.ui.helper.resource

import android.content.Context
import com.example.helper.resource.IResourceHelper
import com.example.helper.resource.StringResource
import com.example.ui.R

class ResourceHelper(
    private val context: Context
) : IResourceHelper {

    override fun extractStringResource(resource: StringResource): String = when (resource) {
        is StringResource.Common -> extractResource(resource)
    }

    private fun extractResource(resource: StringResource.Common): String = when (resource) {
        StringResource.Common.CommonCancellation -> context.getString(R.string.common_no)
        StringResource.Common.CommonConfirmation -> context.getString(R.string.common_yes)
        StringResource.Common.ConfirmExitString ->
            context.getString(R.string.email_confirmation_exit_confirmation_text)
    }
}