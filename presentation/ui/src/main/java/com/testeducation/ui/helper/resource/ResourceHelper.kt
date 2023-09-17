package com.testeducation.ui.helper.resource

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.testeducation.helper.resource.ColorResource
import com.testeducation.helper.resource.DrawableResource
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.helper.resource.StringResource
import com.testeducation.logic.model.test.CardTestStyle
import com.testeducation.ui.R

class ResourceHelper(
    private val context: Context
) : IResourceHelper {

    override fun extractStringResource(resource: StringResource): String = when (resource) {
        is StringResource.Common -> extractResource(resource)
        is StringResource.Error -> extractErrorStringResource(resource)
        is StringResource.Update -> extractUpdateResource(resource)
        is StringResource.Question -> extractQuestionResource(resource)
    }

    override fun extractColorResource(resource: ColorResource): Int = when (resource) {
        is ColorResource.Main -> extractMainColorResource(resource)
        is ColorResource.MainLight -> extractMainColorResource(resource)
        is ColorResource.Secondary -> extractSecondaryColorResource(resource)
    }

    override fun extractDrawableResource(resource: DrawableResource): Int = when (resource) {
        DrawableResource.MatchIconQuestion -> R.drawable.ic_answer_match
        DrawableResource.DefaultIconQuestion -> R.drawable.ic_answer_choosing
        DrawableResource.WriteAnswerIconQuestion -> R.drawable.ic_answer_write
    }


    override fun getDrawableStyleTestCard(style: CardTestStyle): Int = when (style) {
        CardTestStyle.X -> {
            R.drawable.ic_card_x
        }

        CardTestStyle.O -> {
            R.drawable.ic_card_circle
        }

        CardTestStyle.DOTTED -> {
            R.drawable.ic_card_dots
        }

        CardTestStyle.ELLIPSE -> {
            R.drawable.ic_card_ellipse
        }
    }

    private fun extractResource(resource: StringResource.Common): String = when (resource) {
        StringResource.Common.CommonCancellation -> context.getString(R.string.common_no)
        StringResource.Common.CommonConfirmation -> context.getString(R.string.common_yes)
        StringResource.Common.ConfirmExitString ->
            context.getString(R.string.email_confirmation_exit_confirmation_text)

        StringResource.Common.CommonBack -> string(R.string.common_back)
        StringResource.Common.CommonCancel -> string(R.string.common_cancel)
        StringResource.Common.CommonNext -> string(R.string.common_next)
        StringResource.Common.CommonSave -> string(R.string.common_save)
    }

    private fun extractErrorStringResource(resource: StringResource.Error): String =
        when (resource) {
            StringResource.Error.EmailIsEmptyString -> context.getString(R.string.error_email_empty)
            StringResource.Error.PasswordIsEmptyString -> context.getString(R.string.error_password_empty)
            StringResource.Error.TitleCreationTestEmpty -> string(R.string.error_title_creation_test_empty)
        }

    private fun extractMainColorResource(resource: ColorResource.Main) = when (resource) {
        ColorResource.Main.Blue -> color(R.color.colorBlue)
        ColorResource.Main.Red -> color(R.color.colorRed)
        ColorResource.Main.Green -> color(R.color.colorDarkGreen)
        ColorResource.Main.Orange -> color(R.color.colorOrange)
        ColorResource.Main.White -> color(R.color.colorWhite)
    }

    private fun extractMainColorResource(resource: ColorResource.MainLight) = when (resource) {
        ColorResource.MainLight.Blue -> color(R.color.colorMainBlueLight)
        ColorResource.MainLight.Red -> color(R.color.colorMainRedLight)
        ColorResource.MainLight.Green -> color(R.color.colorMainGreenLight)
        ColorResource.MainLight.Orange -> color(R.color.colorMainOrangeLight)
    }

    private fun extractSecondaryColorResource(resource: ColorResource.Secondary) = when (resource) {
        ColorResource.Secondary.Gray1 -> color(R.color.colorGray_1)
    }

    private fun extractUpdateResource(resource: StringResource.Update) = when (resource) {
        StringResource.Update.UpdateRequiredError -> context.getString(R.string.app_update_required)
    }

    private fun extractQuestionResource(resource: StringResource.Question) = when (resource) {
        is StringResource.Question.NumberQuestion -> context.getString(
            R.string.question_number,
            resource.number.toString()
        )
    }

    private fun color(@ColorRes id: Int) = ContextCompat.getColor(context, id)

    private fun string(@StringRes id: Int) = context.getString(id)
}
