package com.testeducation.ui.helper.extractor

import android.content.Context
import com.testeducation.helper.resource.ColorResource
import com.testeducation.helper.resource.DrawableResource
import com.testeducation.helper.resource.StringResource
import com.testeducation.ui.R

fun StringResource.OnBoarding.extract(context: Context) = when(this) {
    StringResource.OnBoarding.TitleFirst -> context.getString(R.string.onboarding_title_first)
    StringResource.OnBoarding.TitleSecond -> context.getString(R.string.onboarding_title_second)
    StringResource.OnBoarding.TitleThree -> context.getString(R.string.onboarding_title_three)
    StringResource.OnBoarding.TitleFour -> context.getString(R.string.onboarding_title_four)
    StringResource.OnBoarding.DescriptionFirst -> context.getString(R.string.onboarding_description_first)
    StringResource.OnBoarding.DescriptionSecond -> context.getString(R.string.onboarding_description_second)
    StringResource.OnBoarding.DescriptionThree -> context.getString(R.string.onboarding_description_three)
}

fun DrawableResource.OnBoarding.extract() = when(this) {
    DrawableResource.OnBoarding.PageFirst -> R.drawable.onboarding_first
    DrawableResource.OnBoarding.PageSecond -> R.drawable.onboarding_second
    DrawableResource.OnBoarding.PageThree ->  R.drawable.onboarding_three
    DrawableResource.OnBoarding.PageFour -> R.drawable.onboarding_four
    DrawableResource.OnBoarding.GradientFirst -> R.drawable.background_gradient_blick
    DrawableResource.OnBoarding.GradientSecond -> R.drawable.background_gradient_blick_second
    DrawableResource.OnBoarding.GradientThree -> R.drawable.background_gradient_blick_three
    DrawableResource.OnBoarding.GradientFour -> R.drawable.background_gradient_blick_four
}

fun ColorResource.OnBoarding.extract(context: Context) = when(this) {
    ColorResource.OnBoarding.PageFirst -> context.getColor(R.color.onboarding_green_first)
    ColorResource.OnBoarding.PageSecond -> context.getColor(R.color.onboarding_green_second)
    ColorResource.OnBoarding.PageThree -> context.getColor(R.color.onboarding_green_three)
    ColorResource.OnBoarding.PageFour -> context.getColor(R.color.onboarding_green_four)
}