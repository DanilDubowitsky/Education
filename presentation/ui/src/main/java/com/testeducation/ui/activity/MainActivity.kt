package com.testeducation.ui.activity

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import com.testeducation.activity.main.MainActivityViewModel
import com.testeducation.logic.activity.MainActivitySideEffect
import com.testeducation.navigation.core.AnimationSet
import com.testeducation.navigation.core.IScreenAdapter
import com.testeducation.navigation.core.NavigationHost
import com.testeducation.navigation.core.Navigator
import com.testeducation.ui.R
import com.testeducation.ui.base.activity.ViewModelHostActivity
import com.testeducation.ui.databinding.ActivityMainBinding
import com.testeducation.ui.utils.loadColor
import org.orbitmvi.orbit.viewmodel.observe
import java.util.Locale
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
            activity = this,
            containerId = R.id.fragmentContainer,
            screenAdapter = screenAdapter,
            moveAnimationSet = AnimationSet(
                com.testeducation.navigation.R.anim.slide_in,
                com.testeducation.navigation.R.anim.fade_out,
                com.testeducation.navigation.R.anim.fade_in,
                com.testeducation.navigation.R.anim.slide_out,
            ),
            replaceAnimationSet = AnimationSet(
                com.testeducation.navigation.R.anim.fade_in,
                com.testeducation.navigation.R.anim.fade_out,
                com.testeducation.navigation.R.anim.fade_in,
                com.testeducation.navigation.R.anim.fade_out
            )
        )
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(updateLocale(newBase))
    }

    private fun updateLocale(context: Context): Context? {
        val ruLocale = Locale("ru")
        Locale.setDefault(ruLocale)
        val configuration: Configuration = context.resources.configuration
        configuration.setLocale(ruLocale)
        configuration.setLayoutDirection(ruLocale)
        return context.createConfigurationContext(configuration)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Education)
        window.statusBarColor = loadColor(R.color.colorBlue)
        window.navigationBarColor = loadColor(R.color.colorBlue)
        super.onCreate(savedInstanceState)
        viewBinding.screenPlaceholder.isVisible = true
        if (savedInstanceState == null) {
            viewModel.prepare()
        } else {
            hideSplash()
        }
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        observeData()
    }

    private fun observeData() = viewModel.observe(this, sideEffect = ::handleSideEffect)

    private fun handleSideEffect(sideEffect: MainActivitySideEffect) = when (sideEffect) {
        MainActivitySideEffect.OnDataLoaded -> {
            hideSplash()
        }
    }

    private fun hideSplash() {
        window.statusBarColor = loadColor(android.R.color.transparent)
        window.navigationBarColor = loadColor(android.R.color.transparent)
        viewBinding.screenPlaceholder.isVisible = false
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