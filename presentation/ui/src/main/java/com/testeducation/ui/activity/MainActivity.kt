package com.testeducation.ui.activity

import android.os.Bundle
import androidx.core.view.WindowCompat
import com.testeducation.activity.main.MainActivityViewModel
import com.testeducation.navigation.core.IScreenAdapter
import com.testeducation.navigation.core.NavigationHost
import com.testeducation.navigation.core.Navigator
import com.testeducation.ui.R
import com.testeducation.ui.base.activity.ViewModelHostActivity
import com.testeducation.ui.databinding.ActivityMainBinding
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
        if (savedInstanceState == null) viewModel.prepare()
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