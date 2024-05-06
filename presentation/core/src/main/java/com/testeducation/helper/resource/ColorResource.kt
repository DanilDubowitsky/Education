package com.testeducation.helper.resource

sealed interface ColorResource {
    sealed interface Main : ColorResource {
        object Blue : Main
        object Red : Main
        object Green : Main
        object Orange: Main
        object White: Main
        object Black: Main
    }

    sealed interface MainLight: ColorResource {
        object Blue : MainLight
        object Red : MainLight
        object Green : MainLight
        object Orange: MainLight
    }

    sealed interface Secondary: ColorResource {
        object Gray1: Secondary
        object ColorGrayBlueDisable: Secondary
        object ColorDarkBlue: Secondary
    }

    sealed interface OnBoarding: ColorResource {
        object PageFirst: OnBoarding
        object PageSecond: OnBoarding
        object PageThree: OnBoarding
        object PageFour: OnBoarding
    }
}