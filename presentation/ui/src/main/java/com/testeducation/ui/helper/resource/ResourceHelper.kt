package com.testeducation.ui.helper.resource

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.testeducation.helper.resource.AnswerColorResource
import com.testeducation.helper.resource.ColorResource
import com.testeducation.helper.resource.DrawableResource
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.helper.resource.StringResource
import com.testeducation.logic.model.test.CardTestStyle
import com.testeducation.ui.R
import com.testeducation.ui.helper.extractor.extractResourceId

class ResourceHelper(
    private val context: Context
) : IResourceHelper {

    override fun extractStringResource(resource: StringResource): String = when (resource) {
        is StringResource.Common -> extractResource(resource)
        is StringResource.Error -> extractErrorStringResource(resource)
        is StringResource.Update -> extractUpdateResource(resource)
        is StringResource.Question -> extractQuestionResource(resource)
        is StringResource.StringSettings -> extractStringSettingResource(resource)
        is StringResource.Validate -> extractValidateStringResource(resource)
    }

    override fun extractColorResource(resource: ColorResource): Int = when (resource) {
        is ColorResource.Main -> extractMainColorResource(resource)
        is ColorResource.MainLight -> extractMainColorResource(resource)
        is ColorResource.Secondary -> extractSecondaryColorResource(resource)
        is AnswerColorResource -> color(resource.extractResourceId())
    }

    override fun extractDrawableResource(resource: DrawableResource): Int = when (resource) {
        DrawableResource.MatchIconQuestion -> R.drawable.ic_answer_match
        DrawableResource.DefaultIconQuestion -> R.drawable.ic_answer_choosing
        DrawableResource.WriteAnswerIconQuestion -> R.drawable.ic_answer_write
        DrawableResource.OrderAnswerIconQuestion -> R.drawable.ic_answer_order
        is DrawableResource.Avatar -> extractAvatarDrawableResource(resource)
    }

    private fun extractAvatarDrawableResource(avatar: DrawableResource.Avatar) = when(avatar) {
        DrawableResource.Avatar.Default -> R.drawable.ic_profile_avatar
        DrawableResource.Avatar.First -> R.drawable.ic_profile_avatar
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
            StringResource.Error.TitleCreationTestMaxLine -> string(R.string.error_title_creation_max_line)
        }

    private fun extractMainColorResource(resource: ColorResource.Main) = when (resource) {
        ColorResource.Main.Blue -> color(R.color.colorBlue)
        ColorResource.Main.Red -> color(R.color.colorRed)
        ColorResource.Main.Green -> color(R.color.colorDarkGreen)
        ColorResource.Main.Orange -> color(R.color.colorOrange)
        ColorResource.Main.White -> color(R.color.colorWhite)
        ColorResource.Main.Black -> color(R.color.colorTextPrimary)
    }

    private fun extractMainColorResource(resource: ColorResource.MainLight) = when (resource) {
        ColorResource.MainLight.Blue -> color(R.color.colorMainBlueLight)
        ColorResource.MainLight.Red -> color(R.color.colorMainRedLight)
        ColorResource.MainLight.Green -> color(R.color.colorMainGreenLight)
        ColorResource.MainLight.Orange -> color(R.color.colorMainOrangeLight)
    }

    private fun extractSecondaryColorResource(resource: ColorResource.Secondary) = when (resource) {
        ColorResource.Secondary.Gray1 -> color(R.color.colorGray_1)
        ColorResource.Secondary.ColorGrayBlueDisable -> color(R.color.colorGrayBlueDisabled)
        ColorResource.Secondary.ColorDarkBlue -> color(R.color.colorDarkBlue)
    }

    private fun extractUpdateResource(resource: StringResource.Update) = when (resource) {
        StringResource.Update.UpdateRequiredError -> context.getString(R.string.app_update_required)
    }

    private fun extractQuestionResource(resource: StringResource.Question) = when (resource) {
        is StringResource.Question.NumberQuestion -> context.getString(
            R.string.question_number,
            resource.number.toString()
        )

        is StringResource.Question.TimeQuestionMore -> context.getString(
            R.string.question_time_more_that_minute,
            resource.minutes, resource.seconds
        )

        is StringResource.Question.TimeQuestionOnlyMinutes -> context.getString(
            R.string.question_time_only_minute,
            resource.minutes
        )

        is StringResource.Question.TimeQuestionLess -> context.getString(
            R.string.question_time_less_that_minute,
            resource.seconds
        )

        is StringResource.Question.MaxLengthAnswer -> context.getString(
            R.string.question_answer_max_length,
            resource.count.toString()
        )
    }

    private fun extractStringSettingResource(resource: StringResource.StringSettings) =
        when (resource) {
            is StringResource.StringSettings.TestTitle -> string(R.string.test_settings_test_title)
            is StringResource.StringSettings.DesignTitle -> string(R.string.test_settings_test_design)
            is StringResource.StringSettings.ThemeTitle -> string(R.string.test_settings_theme_title)
            is StringResource.StringSettings.AntiCheatTitle -> string(R.string.test_settings_anti_cheat_title)
            is StringResource.StringSettings.AntiCheatDescription -> string(R.string.test_settings_anti_cheat_description)
            is StringResource.StringSettings.MinCorrectAnswerTitle -> string(R.string.test_settings_min_correct_answer_title)
            is StringResource.StringSettings.PreShowQuestionTitle -> string(R.string.test_settings_show_question_title)
            is StringResource.StringSettings.QuestionOrderTitle -> string(R.string.test_settings_order_title)
            is StringResource.StringSettings.TestAvailabilityTitle -> string(R.string.test_settings_availability_title)
            is StringResource.StringSettings.OrderValueOrder -> string(R.string.test_settings_order_value_order)
            is StringResource.StringSettings.OrderValueRandom -> string(R.string.test_settings_order_value_random)
            is StringResource.StringSettings.AvailabilityValueAll -> string(R.string.test_settings_availability_all)
            is StringResource.StringSettings.AvailabilityValueLink -> string(R.string.test_settings_availability_link)
        }

    private fun extractValidateStringResource(resource: StringResource.Validate) = when (resource) {
        is StringResource.Validate.TestEditErrorTitle -> string(R.string.test_edit_error_title)
        is StringResource.Validate.QuestionCreationErrorTitle -> string(R.string.question_creation_error_title)
        is StringResource.Validate.EmptyQuestionCreation -> string(R.string.question_creation_validate_empty)
        is StringResource.Validate.MaxQuestionValue -> context.getString(R.string.test_edit_max_value_question, resource.count.toString())
        is StringResource.Validate.OneAnswerQuestionCreation -> string(R.string.question_creation_validate_one_answer)
        is StringResource.Validate.MinOneTrueAnswer -> string(R.string.question_creation_validate_min_one_true_answer)
        is StringResource.Validate.AnswerIsEmpty -> string(R.string.question_creation_validate_answer_is_empty)
        is StringResource.Validate.MinCountAnswer -> context.getString(R.string.question_creation_validate_min_count_answer, resource.count.toString())
    }

    private fun color(@ColorRes id: Int) = ContextCompat.getColor(context, id)

    private fun string(@StringRes id: Int) = context.getString(id)
}
