package com.example.ui.activity

import android.os.Bundle
import com.example.activity.main.MainActivityViewModel
import com.example.navigation.core.IScreenAdapter
import com.example.navigation.core.NavigationHost
import com.example.navigation.core.Navigator
import com.example.ui.R
import com.example.ui.base.activity.ViewModelHostActivity
import com.example.ui.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : ViewModelHostActivity<MainActivityViewModel, ActivityMainBinding>(
    MainActivityViewModel::class,
    ActivityMainBinding::inflate
) {

    @Inject
    lateinit var navigationHost: NavigationHost

    @Inject
    lateinit var screenAdapter: IScreenAdapter

    private val navigator by lazy {
        Navigator(
            this,
            R.id.fragmentContainer,
            screenAdapter
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigateToLogin()
    }

    private fun setNavigator() {
        navigationHost.setNavigator(navigator)
    }

    private fun removeNavigator() {
        navigationHost.removeNavigator(null)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        setNavigator()
    }

    override fun onPause() {
        removeNavigator()
        super.onPause()
    }

}