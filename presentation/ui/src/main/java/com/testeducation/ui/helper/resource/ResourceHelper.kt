package com.testeducation.ui.helper.resource

import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.testeducation.helper.resource.ColorResource
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.helper.resource.StringResource
import com.testeducation.ui.R

class ResourceHelper(
    private val context: Context
) : IResourceHelper {

    override fun extractStringResource(resource: StringResource): String = when (resource) {
        is StringResource.Common -> extractResource(resource)
        is StringResource.Error -> extractErrorStringResource(resource)
    }

    override fun extractColorResource(resource: ColorResource): Int = when (resource) {
        is ColorResource.Main -> extractMainColorResource(resource)
    }

    private fun extractResource(resource: StringResource.Common): String = when (resource) {
        StringResource.Common.CommonCancellation -> context.getString(R.string.common_no)
        StringResource.Common.CommonConfirmation -> context.getString(R.string.common_yes)
        StringResource.Common.ConfirmExitString ->
            context.getString(R.string.email_confirmation_exit_confirmation_text)
    }

    private fun extractErrorStringResource(resource: StringResource.Error): String =
        when (resource) {
            StringResource.Error.EmailIsEmptyString -> context.getString(R.string.error_email_empty)
            StringResource.Error.PasswordIsEmptyString -> context.getString(R.string.error_password_empty)
        }

    private fun extractMainColorResource(resource: ColorResource.Main) = when (resource) {
        ColorResource.Main.Blue -> color(R.color.colorBlue)
        ColorResource.Main.Red -> color(R.color.colorRed)
        ColorResource.Main.Green -> color(R.color.colorDarkGreen)
        ColorResource.Main.Orange -> color(R.color.colorOrange)
    }

    private fun color(@ColorRes id: Int) = ContextCompat.getColor(context, id)
}
