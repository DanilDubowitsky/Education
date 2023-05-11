package com.example.navigation.core

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.FragmentManager

class Navigator(
    private val activity: FragmentActivity,
    private val containerId: Int,
    private val screenAdapter: IScreenAdapter,
    private val fragmentManager: FragmentManager = activity.supportFragmentManager,
    private val fragmentFactory: FragmentFactory = fragmentManager.fragmentFactory,
) : INavigator {

    override fun executeCommand(command: Command) {
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
            is Screen.FragmentScreen -> moveFragment(screen)
            is Screen.DialogScreen -> moveDialog(screen)
        }
    }

    private fun moveDialog(screen: Screen.DialogScreen) {
        val dialog = screen.createDialog(fragmentFactory)
        dialog.showNow(fragmentManager, screen.javaClass.name)
    }

    private fun executeReplaceCommand(command: Command.Replace) {
        when (val screen = screenAdapter.createPlatformScreen(command.screen)) {
            is Screen.ActivityScreen -> {
                // TODO: add activity support
            }
            is Screen.FragmentScreen -> replaceFragment(screen)
            is Screen.DialogScreen -> moveDialog(screen)
        }
    }

    private fun moveFragment(screen: Screen.FragmentScreen) {
        val fragment = screen.createFragment(fragmentFactory)
        fragmentManager.beginTransaction().replace(containerId, fragment, screen.javaClass.name)
            .addToBackStack(null)
            .commit()
    }

    private fun replaceFragment(screen: Screen.FragmentScreen) {
        val fragment = screen.createFragment(fragmentFactory)
        fragmentManager.beginTransaction().replace(containerId, fragment, screen.javaClass.name)
            .commit()
    }

}
