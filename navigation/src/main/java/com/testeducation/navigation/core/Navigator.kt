package com.testeducation.navigation.core

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.FragmentManager

class Navigator(
    private val activity: FragmentActivity,
    private val containerId: Int,
    private val screenAdapter: IScreenAdapter,
    private val fragmentManager: FragmentManager = activity.supportFragmentManager,
    private val fragmentFactory: FragmentFactory = fragmentManager.fragmentFactory,
    private val animationSet: AnimationSet? = null
) : INavigator {

    // TODO: add screen history
    private var currentVisibleScreen: Screen? = null

    override fun executeCommand(command: Command) {
        // TODO: add support for dialog back press
        when (command) {
            is Command.Back -> handleBackCommand()
            is Command.Forward -> executeForwardCommand(command)
            is Command.Replace -> executeReplaceCommand(command)
            is Command.NewRootChain -> executeRootChainCommand(command)
        }
    }

    private fun executeForwardCommand(command: Command.Forward) {
        when (val screen = screenAdapter.createPlatformScreen(command.screen)) {
            is Screen.ActivityScreen -> {
                // TODO: add activity support
            }

            is Screen.DialogScreen -> moveDialog(screen)
            is Screen.FragmentScreen -> moveFragment(screen)
        }
    }

    private fun handleBackCommand() {
        val fragment = fragmentManager.findFragmentByTag(currentVisibleScreen!!::class.java.name)
        if (fragment is DialogFragment) {
            fragment.dismiss()
        } else {
            fragmentManager.popBackStack()
        }
    }

    private fun executeRootChainCommand(command: Command.NewRootChain) {
        when (val screen = screenAdapter.createPlatformScreen(command.screen)) {
            is Screen.ActivityScreen -> TODO("add activity support")
            is Screen.DialogScreen -> chainWithFragment(
                screen.createDialog(fragmentFactory),
                screen
            )

            is Screen.FragmentScreen -> chainWithFragment(
                screen.createFragment(fragmentFactory),
                screen
            )
        }
    }

    private fun chainWithFragment(fragment: Fragment, screen: Screen) {
        for (i in 0 until fragmentManager.backStackEntryCount) {
            fragmentManager.popBackStack()
        }
        if (fragment is DialogFragment) {
            fragment.showNow(fragmentManager, screen::class.java.name)
        } else {
            fragmentManager.beginTransaction()
                .replace(containerId, fragment, screen::class.java.name)
                .setReorderingAllowed(true)
                .commit()
        }
        currentVisibleScreen = screen
    }

    private fun moveDialog(screen: Screen.DialogScreen) {
        if (screen == currentVisibleScreen) return
        val dialog = screen.createDialog(fragmentFactory)
        dialog.showNow(fragmentManager, screen::class.java.name)
        currentVisibleScreen = screen
    }

    private fun executeReplaceCommand(command: Command.Replace) {
        when (val screen = screenAdapter.createPlatformScreen(command.screen)) {
            is Screen.ActivityScreen -> {
                // TODO: add activity support
            }
            is Screen.DialogScreen -> moveDialog(screen)
            is Screen.FragmentScreen -> replaceFragment(screen)
        }
    }

    private fun moveFragment(screen: Screen.FragmentScreen) {
        if (screen == currentVisibleScreen) return
        val fragment = screen.createFragment(fragmentFactory)
        val transaction = fragmentManager.beginTransaction()
        if (animationSet != null) {
            transaction.setCustomAnimations(
                animationSet.enterAnim,
                animationSet.exitAnim,
                animationSet.popEnterAnim,
                animationSet.popExitAnim
            )
        }
        transaction.replace(containerId, fragment, screen::class.java.name)
        transaction.addToBackStack(null)
        transaction.setReorderingAllowed(true)
        transaction.commit()
        currentVisibleScreen = screen
    }

    private fun replaceFragment(screen: Screen.FragmentScreen) {
        if (screen == currentVisibleScreen) return
        val fragment = screen.createFragment(fragmentFactory)
        fragmentManager.beginTransaction().replace(containerId, fragment, screen::class.java.name)
            .setReorderingAllowed(true)
            .commit()
        currentVisibleScreen = screen
    }

}
