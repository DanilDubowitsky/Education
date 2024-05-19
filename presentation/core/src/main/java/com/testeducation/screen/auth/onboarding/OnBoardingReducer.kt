package com.testeducation.screen.auth.onboarding

import com.testeducation.core.IReducer
import com.testeducation.helper.resource.DrawableResource
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.helper.resource.StringResource
import com.testeducation.logic.model.auth.OnBoardingItemUi
import com.testeducation.logic.screen.auth.onboarding.OnBoardingState
import com.testeducation.utils.getDrawable
import com.testeducation.utils.getString

class OnBoardingReducer(private val resource: IResourceHelper) : IReducer<OnBoardingModelState, OnBoardingState> {
    override fun reduce(modelState: OnBoardingModelState): OnBoardingState {
        return OnBoardingState(
            0,
            getListOnBoarding()
        )
    }

    private fun getListOnBoarding(): List<OnBoardingItemUi> {
        return listOf(
            OnBoardingItemUi(
                title = StringResource.OnBoarding.TitleFirst.getString(resource),
                description = StringResource.OnBoarding.DescriptionFirst.getString(resource),
                color = DrawableResource.OnBoarding.GradientFirst.getDrawable(resource),
                image = DrawableResource.OnBoarding.PageFirst.getDrawable(resource)
            ),
            OnBoardingItemUi(
                title = StringResource.OnBoarding.TitleSecond.getString(resource),
                description = StringResource.OnBoarding.DescriptionSecond.getString(resource),
                color = DrawableResource.OnBoarding.GradientSecond.getDrawable(resource),
                image = DrawableResource.OnBoarding.PageSecond.getDrawable(resource)
            ),
            OnBoardingItemUi(
                title = StringResource.OnBoarding.TitleThree.getString(resource),
                description = StringResource.OnBoarding.DescriptionThree.getString(resource),
                color = DrawableResource.OnBoarding.GradientThree.getDrawable(resource),
                image = DrawableResource.OnBoarding.PageThree.getDrawable(resource)
            ),
            OnBoardingItemUi(
                title = StringResource.OnBoarding.TitleFour.getString(resource),
                description = "",
                color = DrawableResource.OnBoarding.GradientFour.getDrawable(resource),
                image = DrawableResource.OnBoarding.PageFour.getDrawable(resource)
            )
        )
    }
}