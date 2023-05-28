package com.testeducation.navigation.core

import android.content.Context
import android.content.Intent
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory

sealed interface Screen {

    interface FragmentScreen : Screen {
        fun createFragment(
            fragmentFactory: FragmentFactory
        ): Fragment

        companion object {
            operator fun invoke(
                fragmentCreator: ScreenCreator<FragmentFactory, Fragment>
            ): FragmentScreen =
                object : FragmentScreen {
                    override fun createFragment(fragmentFactory: FragmentFactory): Fragment =
                        fragmentCreator.create(fragmentFactory)
                }
        }
    }

    interface DialogScreen : Screen {
        fun createDialog(
            fragmentFactory: FragmentFactory
        ): DialogFragment

        companion object {
            operator fun invoke(fragmentCreator: ScreenCreator<FragmentFactory, DialogFragment>) =
                object : DialogScreen {
                    override fun createDialog(fragmentFactory: FragmentFactory): DialogFragment {
                        return fragmentCreator.create(fragmentFactory)
                    }
                }
        }
    }

    interface ActivityScreen : Screen {
        fun createIntent(
            context: Context
        ): Intent

        companion object {
            operator fun invoke(
                intentCreator: ScreenCreator<Context, Intent>
            ) = object : ActivityScreen {

                override fun createIntent(context: Context): Intent =
                    intentCreator.create(context)
            }
        }
    }
}