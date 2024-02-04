package com.testeducation.navigation.core

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit

class Navigator(
    private val activity: FragmentActivity,
    private val containerId: Int,
    private val screenAdapter: IScreenAdapter,
    private val fragmentManager: FragmentManager = activity.supportFragmentManager,
    private val fragmentFactory: FragmentFactory = fragmentManager.fragmentFactory,
    private val moveAnimationSet: AnimationSet? = null,
    private val replaceAnimationSet: AnimationSet? = null
) : INavigator {

    private val screenStack: MutableList<String> = mutableListOf()
    private val currentVisibleDialog: Screen.DialogScreen? = null

    override fun executeCommand(command: Command) {
        when (command) {
            is Command.Back -> handleBackCommand()
            is Command.Forward -> executeForwardCommand(command)
            is Command.Replace -> executeReplaceCommand(command)
            is Command.NewRootChain -> executeRootChainCommand(command)
        }
    }

    private fun executeForwardCommand(command: Command.Forward) {
        when (val screen: Screen = screenAdapter.createPlatformScreen(command.screen)) {
            is Screen.ActivityScreen -> {
                // TODO: add activity support
            }

            is Screen.DialogScreen -> moveDialog(screen)
            is Screen.FragmentScreen -> {
                copyStackToLocal()
                moveFragment(screen, command.addToBackStack)
            }
        }
    }

    private fun handleBackCommand() {
        if (screenStack.isNotEmpty()) {
            val fragment = fragmentManager.findFragmentByTag(screenStack[screenStack.lastIndex])
            if (fragment is DialogFragment) {
                fragment.dismiss()
            } else {
                fragmentManager.popBackStack()
            }
            screenStack.removeAt(screenStack.lastIndex)
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
            fragment.showNow(fragmentManager, screen.screenKey)
        } else {
            fragmentManager.beginTransaction()
                .replace(containerId, fragment, screen.screenKey)
                .setReorderingAllowed(true)
                .commit()
        }
        screenStack.clear()
        screenStack.add(screen.screenKey)
    }

    private fun moveDialog(screen: Screen.DialogScreen) {
        val dialog = screen.createDialog(fragmentFactory)
        dialog.showNow(fragmentManager, screen.screenKey)
        screenStack.add(screen.screenKey)
    }

    private fun executeReplaceCommand(command: Command.Replace) {
        when (val screen = screenAdapter.createPlatformScreen(command.screen)) {
            is Screen.ActivityScreen -> {
                // TODO: add activity support
            }
            is Screen.DialogScreen -> moveDialog(screen)
            is Screen.FragmentScreen -> {
                copyStackToLocal()
                replaceFragment(screen)
            }
        }
    }

    private fun moveFragment(screen: Screen.FragmentScreen, addToBackStack: Boolean) {
        val existingFragment = fragmentManager.findFragmentByTag(screen.screenKey)
        if (existingFragment != null) {
            fragmentManager.commit {
                replace(containerId, existingFragment)
            }
            return
        }
        val fragment = screen.createFragment(fragmentFactory)
        val transaction = fragmentManager.beginTransaction()
        if (moveAnimationSet != null) {
            transaction.setCustomAnimations(
                moveAnimationSet.enterAnim,
                moveAnimationSet.exitAnim,
                moveAnimationSet.popEnterAnim,
                moveAnimationSet.popExitAnim
            )
        }
        transaction.replace(containerId, fragment, screen.screenKey)
        if (addToBackStack) {
            transaction.addToBackStack(screen.screenKey)
            screenStack.add(screen.screenKey)
        }
        transaction.setReorderingAllowed(true)
        transaction.commit()
    }

    private fun replaceFragment(screen: Screen.FragmentScreen) {
        val fragment = screen.createFragment(fragmentFactory)
        val transaction = fragmentManager.beginTransaction()
        if (replaceAnimationSet != null) {
            transaction.setCustomAnimations(
                replaceAnimationSet.enterAnim,
                replaceAnimationSet.exitAnim
            )
        }
        transaction.replace(containerId, fragment, screen.screenKey)
            .setReorderingAllowed(true)
            .commit()
    }

    private fun copyStackToLocal() {
        screenStack.clear()
        for (i in 0 until fragmentManager.backStackEntryCount) {
            screenStack.add(fragmentManager.getBackStackEntryAt(i).name!!)
        }
    }
}
