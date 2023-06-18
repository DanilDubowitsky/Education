package com.testeducation.navigation.core

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
            is Command.Back -> fragmentManager.popBackStack()
            is Command.Forward -> executeForwardCommand(command)
            is Command.Replace -> executeReplaceCommand(command)
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

    private fun moveDialog(screen: Screen.DialogScreen) {
        val dialog = screen.createDialog(fragmentFactory)
        dialog.showNow(fragmentManager, screen.javaClass.name)
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
        val fragment = screen.createFragment(fragmentFactory)
        val transaction = fragmentManager.beginTransaction()
            .addToBackStack(null)
        if (animationSet != null) {
            transaction.setCustomAnimations(
                animationSet.enterAnim,
                animationSet.exitAnim,
                animationSet.popEnterAnim,
                animationSet.popExitAnim
            )
        }
        transaction.replace(containerId, fragment, screen.javaClass.name)
        transaction.commit()
        currentVisibleScreen = screen
    }

    private fun replaceFragment(screen: Screen.FragmentScreen) {
        val fragment = screen.createFragment(fragmentFactory)
        fragmentManager.beginTransaction().replace(containerId, fragment, screen.javaClass.name)
            .commit()
        currentVisibleScreen = screen
    }

}
